package chungbazi.chungbazi_be.domain.community.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.community.converter.CommunityConverter;
import chungbazi.chungbazi_be.domain.community.dto.CommunityRequestDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Heart;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.CommentRepository;
import chungbazi.chungbazi_be.domain.community.repository.HeartRepository;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.notification.service.FCMTokenService;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyDetailsResponse;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.service.UserService;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.s3.S3Manager;
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
    private final NotificationRepository notificationRepository;
    private final FCMTokenService fcmTokenService;
    private final NotificationService notificationService;
    private final UserHelper userHelper;
    private final HeartRepository heartRepository;

    public CommunityResponseDTO.TotalPostListDto getPosts(Category category, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Post> posts;

        if (category == null || category.toString().isEmpty()){ // 전체 게시글 조회
            posts = (cursor == 0)
                    ? postRepository.findByOrderByIdDesc(pageable).getContent()
                    : postRepository.findByIdLessThanOrderByIdDesc(cursor, pageable).getContent();
        } else { // 카테고리별 게시글 조회
            posts = (cursor == 0)
                    ? postRepository.findByCategoryOrderByIdDesc(category, pageable).getContent()
                    : postRepository.findByCategoryAndIdLessThanOrderByIdDesc(category, cursor, pageable).getContent();
        }

        boolean hasNext = posts.size() > size;
        Long nextCursor = 0L;

        if(hasNext) {
            Post lastPost = posts.get(size - 1);
            nextCursor = lastPost.getId();
            posts = posts.subList(0, size);
        }

        List<CommunityResponseDTO.PostListDto> postList = CommunityConverter.toPostListDto(posts, commentRepository);
        Long totalPostCount = postRepository.countPostByCategory(category);

        return CommunityConverter.toTotalPostListDto(totalPostCount, postList, nextCursor, hasNext);
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
                .build();
        postRepository.save(post);

        Long commentCount = commentRepository.countByPostId(post.getId());

        rewardService.checkRewards();

        return CommunityConverter.toUploadAndGetPostDto(post, commentCount);
    }

    public CommunityResponseDTO.UploadAndGetPostDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        // 자신의 조회는 조회수 증가 제외
        User user = userHelper.getAuthenticatedUser();

        if(!post.getAuthor().getId().equals(user.getId())){
            post.incrementViews(); // 조회수 증가
        }
        Long commentCount = commentRepository.countByPostId(postId);

        return CommunityConverter.toUploadAndGetPostDto(post, commentCount);
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
                .build();

        commentRepository.save(comment);

        if(user.getNotificationSetting().isCommunityAlarm() && !user.getId().equals(post.getAuthor().getId())){
            sendCommunityNotification(post.getId());
        }
        rewardService.checkRewards();

        return CommunityConverter.toUploadAndGetCommentDto(comment);
    }

    public CommunityResponseDTO.CommentListDto getComments(Long postId, Long cursor, int size){
        Pageable pageable = PageRequest.of(0, size + 1);

        List<Comment> comments;
        if (cursor == 0) {
            comments = commentRepository.findByPostIdOrderByIdAsc(postId, pageable).getContent();
        } else {
            comments = commentRepository.findByPostIdAndIdGreaterThanOrderByIdAsc(postId, cursor, pageable).getContent();
        }

        boolean hasNext = comments.size() > size;
        Long nextCursor = 0L;

        if(hasNext) {
            Comment lastComment = comments.get(size - 1);
            nextCursor = lastComment.getId();
            comments = comments.subList(0, size);
        }

        List<CommunityResponseDTO.UploadAndGetCommentDto> commentsList = CommunityConverter.toListCommentDto(comments);

        return CommunityConverter.toGetCommentsListDto(commentsList, nextCursor, hasNext);
    }

    public void likePost(Long postId){
        User user = userHelper.getAuthenticatedUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

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
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        Heart heart = heartRepository.findByUserAndPost(user,post)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_LIKE));
        heartRepository.delete(heart);

        post.decrementLike();
        postRepository.save(post);
    }

    public void sendCommunityNotification(Long postId){
        User user = userHelper.getAuthenticatedUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        User author=post.getAuthor();
        String message=user.getName()+"님이 회원님의 게시글에 댓글을 달았습니다.";

        notificationService.sendNotification(author, NotificationType.COMMUNITY_ALARM, message, post, null);
    }
}
