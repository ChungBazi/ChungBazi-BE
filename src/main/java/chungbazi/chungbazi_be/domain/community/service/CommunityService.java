package chungbazi.chungbazi_be.domain.community.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.community.dto.CommunityRequestDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.s3.S3Manager;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Manager s3Manager;
    public CommunityResponseDTO.UploadPostDto uploadPost(CommunityRequestDTO.UploadPostDto uploadPostDto, List<MultipartFile> imageList){

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

        return CommunityResponseDTO.UploadPostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .views(post.getViews())
                .userId(user.getId())
                .userName(user.getName())
                .reward(user.getReward())
                .characterImg(user.getCharacterImg())
                .thumbnailUrl(post.getThumbnailUrl())
                .imageUrls(uploadedUrls)
                .build();

    }

}
