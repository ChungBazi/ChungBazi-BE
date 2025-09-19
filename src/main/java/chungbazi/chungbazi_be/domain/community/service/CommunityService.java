package chungbazi.chungbazi_be.domain.community.service;

import chungbazi.chungbazi_be.domain.community.converter.CommunityConverter;
import chungbazi.chungbazi_be.domain.community.dto.CommunityRequestDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import chungbazi.chungbazi_be.domain.community.entity.Heart;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.CommentRepository;
import chungbazi.chungbazi_be.domain.community.repository.HeartRepository;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.s3.S3Manager;
import chungbazi.chungbazi_be.global.utils.PaginationResult;
import chungbazi.chungbazi_be.global.utils.PaginationUtil;
import chungbazi.chungbazi_be.global.utils.PopularSearch;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Manager s3Manager;
    private final RewardService rewardService;
    private final NotificationService notificationService;
    private final UserHelper userHelper;
    private final HeartRepository heartRepository;
    private final PopularSearch popularSearch;
    private final UserRepository userRepository;

    public CommunityResponseDTO.TotalPostListDto getPosts(Category category, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Post> posts;

        if (category == null || category.toString().isEmpty()){ // 전체 게시글 조회
            posts = (cursor == 0)
                    ? postRepository.findByStatusOrderByIdDesc(ContentStatus.VISIBLE,pageable).getContent()
                    : postRepository.findByStatusAndIdLessThanOrderByIdDesc(ContentStatus.VISIBLE,cursor, pageable).getContent();
        } else { // 카테고리별 게시글 조회
            posts = (cursor == 0)
                    ? postRepository.findByCategoryAndStatusOrderByIdDesc(category, ContentStatus.VISIBLE, pageable).getContent()
                    : postRepository.findByCategoryAndStatusAndIdLessThanOrderByIdDesc(category, ContentStatus.VISIBLE, cursor, pageable).getContent();
        }

        PaginationResult<Post> paginationResult = PaginationUtil.paginate(posts, size);
        posts = paginationResult.getItems();

        Long currentUserId = isLogin();

        List<CommunityResponseDTO.PostListDto> postList = CommunityConverter.toPostListDto(posts, commentRepository,currentUserId);
        Long totalPostCount = postRepository.countPostByCategoryAndStatus(category,ContentStatus.VISIBLE);

        return CommunityConverter.toTotalPostListDto(
                totalPostCount,
                postList,
                paginationResult.getNextCursor(),
                paginationResult.isHasNext());
    }
    public CommunityResponseDTO.UploadAndGetPostDto uploadPost(CommunityRequestDTO.UploadPostDto uploadPostDto, List<MultipartFile> imageList){

        // 파일 수 검증
        if (imageList != null && imageList.size() > 10) {
            throw new BadRequestHandler(ErrorStatus.FILE_COUNT_EXCEEDED);
        }
        // 유저 조회
        User user = userHelper.getAuthenticatedUser();
        // 파일 업로드
        List<String> uploadedUrls = (imageList != null && !imageList.isEmpty())
                ? s3Manager.uploadMultipleFiles(imageList, "post-images") : new ArrayList<>();

        Post post = Post.builder()
                .title(uploadPostDto.getTitle())
                .content(uploadPostDto.getContent())
                .category(uploadPostDto.getCategory())
                .author(user)
                .views(0)
                .postLikes(0)
                .imageUrls(uploadedUrls)
                .anonymous(uploadPostDto.isAnonymous())
                .status(ContentStatus.VISIBLE)
                .build();
        postRepository.save(post);

        Long commentCount = commentRepository.countByPostIdAndStatus(post.getId(),ContentStatus.VISIBLE);

        rewardService.checkRewards();

        return CommunityConverter.toUploadAndGetPostDto(post, commentCount, true);
    }

    public CommunityResponseDTO.UploadAndGetPostDto getPost(Long postId) {
        Post post = postRepository.getReferenceById(postId);
        // 자신의 조회는 조회수 증가 제외
        User user = userHelper.getAuthenticatedUser();

        if(!post.getAuthor().getId().equals(user.getId())){
            post.incrementViews(); // 조회수 증가
        }
        Long commentCount = commentRepository.countByPostIdAndStatus(postId,ContentStatus.VISIBLE);

        boolean isMine = post.getAuthor().equals(user);

        return CommunityConverter.toUploadAndGetPostDto(post, commentCount, isMine);
    }

    public CommunityResponseDTO.UploadAndGetCommentDto uploadComment(CommunityRequestDTO.UploadCommentDto uploadCommentDto) {
        // 게시글 조회
        Post post = postRepository.findById(uploadCommentDto.getPostId())
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        // 유저 조회
        User user = userHelper.getAuthenticatedUser();
        Comment comment = Comment.builder().
                content(uploadCommentDto.getContent())
                .author(user)
                .post(post)
                .status(ContentStatus.VISIBLE)
                .build();

        commentRepository.save(comment);

        if(user.getNotificationSetting().isCommunityAlarm() && !user.getId().equals(post.getAuthor().getId())){
            sendCommunityNotification(post.getId());
        }
        rewardService.checkRewards();


        return CommunityConverter.toUploadAndGetCommentDto(comment, user.getId());
    }

    public CommunityResponseDTO.CommentListDto getComments(Long postId, Long cursor, int size){
        Pageable pageable = PageRequest.of(0, size + 1);

        List<Comment> comments;
        if (cursor == 0) {
            comments = commentRepository.findByStatusAndPostIdOrderByIdAsc(ContentStatus.VISIBLE,postId, pageable).getContent();
        } else {
            comments = commentRepository.findByStatusAndPostIdAndIdGreaterThanOrderByIdAsc(ContentStatus.VISIBLE,postId, cursor, pageable).getContent();
        }

        PaginationResult<Comment> paginationResult = PaginationUtil.paginate(comments, size);
        comments = paginationResult.getItems();

        Long currentUserId = isLogin();

        List<CommunityResponseDTO.UploadAndGetCommentDto> commentsList = CommunityConverter.toListCommentDto(comments, currentUserId);

        return CommunityConverter.toGetCommentsListDto(
                commentsList,
                paginationResult.getNextCursor(),
                paginationResult.isHasNext());
    }

    public void likePost(Long postId){
        User user = userHelper.getAuthenticatedUser();
        Post post = postRepository.getReferenceById(postId);

        //이미 좋아요한 경우
        if(heartRepository.existsByUserAndPost(user, post)) {
            throw new BadRequestHandler(ErrorStatus.ALREADY_LIKED);
        }

        Heart heart = Heart.builder().user(user).post(post).build();
        heartRepository.save(heart);

        post.incrementLike();
        postRepository.save(post);
    }
    public void unlikePost(Long postId){
        User user = userHelper.getAuthenticatedUser();
        Post post = postRepository.getReferenceById(postId);

        Heart heart = heartRepository.findByUserAndPost(user,post)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_LIKE));
        heartRepository.delete(heart);

        post.decrementLike();
        postRepository.save(post);
    }

    public void sendCommunityNotification(Long postId){
        User user = userHelper.getAuthenticatedUser();

        Post post = postRepository.getReferenceById(postId);

        User author=post.getAuthor();
        String message=user.getName()+"님이 회원님의 게시글에 댓글을 달았습니다.";

        notificationService.sendNotification(author, NotificationType.COMMUNITY_ALARM, message, post, null,null);
    }
    public CommunityResponseDTO.TotalPostListDto getSearchPost(String query, String filter, String period, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Post> posts;
        LocalDateTime startDate = getStartDateByPeriod(period);

        if(!filter.equals("title") && !filter.equals("content")){
            throw new BadRequestHandler(ErrorStatus._BAD_REQUEST);
        }
        String searchField = filter.equals("title") ? "title" : "content";

        if (searchField.equals("title")) { // 제목으로 검색
            posts = (cursor == 0)
                    ? postRepository.findByStatusAndTitleContainingAndCreatedAtAfterOrderByIdDesc(ContentStatus.VISIBLE,query, startDate, pageable).getContent()
                    : postRepository.findByStatusAndTitleContainingAndCreatedAtAfterAndIdLessThanOrderByIdDesc(ContentStatus.VISIBLE,query, startDate, cursor, pageable).getContent();
        } else { // 내용으로 검색
            posts = (cursor == 0)
                    ? postRepository.findByStatusAndContentContainingAndCreatedAtAfterOrderByIdDesc(ContentStatus.VISIBLE,query, startDate, pageable).getContent()
                    : postRepository.findByStatusAndContentContainingAndCreatedAtAfterAndIdLessThanOrderByIdDesc(ContentStatus.VISIBLE,query, startDate, cursor, pageable).getContent();
        }

        popularSearch.updatePopularSearch(query, "community");

        PaginationResult<Post> paginationResult = PaginationUtil.paginate(posts, size);
        posts = paginationResult.getItems();

        Long currentUserId = isLogin();

        List<CommunityResponseDTO.PostListDto> postList = CommunityConverter.toPostListDto(posts, commentRepository,currentUserId);

        return CommunityConverter.toTotalPostListDto(
                null,
                postList,
                paginationResult.getNextCursor(),
                paginationResult.isHasNext());
    }
    private LocalDateTime getStartDateByPeriod(String period) {
        switch (period) {
            case "1d": return LocalDateTime.now().minusDays(1);
            case "7d": return LocalDateTime.now().minusDays(7);
            case "1m": return LocalDateTime.now().minusMonths(1);
            case "3m": return LocalDateTime.now().minusMonths(3);
            case "6m": return LocalDateTime.now().minusMonths(6);
            case "1y": return LocalDateTime.now().minusYears(1);
            default: return LocalDateTime.of(2025, 1, 1, 0, 0); // 전체 조회
        }
    }

    private Long isLogin() {
        Long currentUserId = null;
        try {
            return currentUserId = userHelper.getAuthenticatedUser().getId();
        } catch (NotFoundHandler e) {
            return currentUserId = null;
        } catch (Exception e) {
            return currentUserId = null;
        }
    }

    public void deletePost(Long postId){
        User user = userHelper.getAuthenticatedUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        if (!post.getAuthor().equals(user)) {
            throw new BadRequestHandler(ErrorStatus.UNABLE_TO_DELETE_POST);
        }

        postRepository.delete(post);
    }

    public void deleteComment(Long commentId){
        User user = userHelper.getAuthenticatedUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_COMMENT));

        if (!comment.getAuthor().equals(user)) {
            throw new BadRequestHandler(ErrorStatus.UNABLE_TO_DELETE_COMMENT);
        }

        commentRepository.delete(comment);
    }

}
