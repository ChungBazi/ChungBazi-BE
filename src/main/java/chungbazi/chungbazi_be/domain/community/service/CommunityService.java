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
    public CommunityResponseDTO.UploadPostDto uploadPost(CommunityRequestDTO.UploadPostDto uploadPostDto, MultipartFile imageList){

        // 파일 수 검증
        if (imageList != null && imageList.getSize() > 10) {
            throw new BadRequestHandler(ErrorStatus.FILE_COUNT_EXCEEDED);
        }
        // 유저 조회
        Long userId = SecurityUtils.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        Post post = Post.builder()
                .title(uploadPostDto.getTitle())
                .content(uploadPostDto.getContent())
                .category(uploadPostDto.getCategory())
                .author(user)
                .views(0)
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
                .build();

    }
}
