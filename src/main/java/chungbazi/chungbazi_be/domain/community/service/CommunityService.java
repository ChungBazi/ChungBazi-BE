package chungbazi.chungbazi_be.domain.community.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.community.converter.CommunityConverter;
import chungbazi.chungbazi_be.domain.community.dto.CommunityRequestDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.CommentRepository;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.notification.service.FCMTokenService;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.service.UserService;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Manager s3Manager;
    private final RewardService rewardService;
    private final NotificationRepository notificationRepository;
    private final FCMTokenService fcmTokenService;
    private final NotificationService notificationService;

    public CommunityResponseDTO.TotalPostListDto getPosts(Category category, Long lastPostId, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Post> posts;

        if (category == null || category.toString().isEmpty()){ // 전체 게시글 조회
            posts = (lastPostId == null)
                    ? postRepository.findByOrderByIdDesc(pageable).getContent()
                    : postRepository.findByIdLessThanOrderByIdDesc(lastPostId, pageable).getContent();
        } else { // 카테고리별 게시글 조회
            posts = (lastPostId == null)
                    ? postRepository.findByCategoryOrderByIdDesc(category, pageable).getContent()
                    : postRepository.findByCategoryAndIdLessThanOrderByIdDesc(category, lastPostId, pageable).getContent();
        }
        List<CommunityResponseDTO.PostListDto> postList =
                CommunityConverter.toPostListDto(posts, commentRepository);

        Long totalPostCount = postRepository.countPostByCategory(category);

        return CommunityConverter.toTotalPostListDto(totalPostCount, postList);
    }
    public CommunityResponseDTO.UploadAndGetPostDto uploadPost(CommunityRequestDTO.UploadPostDto uploadPostDto, List<MultipartFile> imageList){

        // 파일 수 검증
        if (imageList != null && imageList.size() > 10) {
            throw new BadRequestHandler(ErrorStatus.FILE_COUNT_EXCEEDED);
        }
        // 유저 조회
        Long userId = SecurityUtils.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        // 파일 업로드
        List<String> uploadedUrls = (imageList != null && !imageList.isEmpty())
                ? s3Manager.uploadMultipleFiles(imageList, "post-images") : new ArrayList<>();

        Post post = Post.builder()
                .title(uploadPostDto.getTitle())
                .content(uploadPostDto.getContent())
                .category(uploadPostDto.getCategory())
                .author(user)
                .views(0)
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
        Long userId = SecurityUtils.getUserId();
        if(!post.getAuthor().getId().equals(userId)){
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
        Long userId = SecurityUtils.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        Comment comment = Comment.builder().
                content(uploadCommentDto.getContent())
                .author(user)
                .post(post)
                .build();

        commentRepository.save(comment);


        rewardService.checkRewards();
        sendCommunityNotification(post.getId());

        return CommunityConverter.toUploadAndGetCommentDto(comment);
    }

    public List<CommunityResponseDTO.UploadAndGetCommentDto> getComments(Long postId, Long lastCommentId, int size){
        Pageable pageable = PageRequest.of(0, size);

        List<Comment> comments;
        if (lastCommentId == null) {
            comments = commentRepository.findByPostIdOrderByIdDesc(postId, pageable).getContent();
        } else {
            comments = commentRepository.findByPostIdAndIdLessThanOrderByIdDesc(postId, lastCommentId, pageable).getContent();
        }

        return CommunityConverter.toGetListCommentDto(comments);
    }

    public void sendCommunityNotification(Long postId){
        User user=userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        Post post=postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_POST));

        User author=post.getAuthor();

        Notification notification=Notification.builder()
                .user(author)
                .type(NotificationType.COMMUNITY_ALARM)
                .message(user.getName()+"님이 회원님의 게시글에 댓글을 달았습니다.")
                .isRead(false)
                .build();

        notificationRepository.save(notification);

        String fcmToken=fcmTokenService.getToken(author.getId());
        if (fcmToken != null) {
            notificationService.pushFCMNotification(fcmToken,notification.getType(),notification.getMessage());
        }
    }


}
