package chungbazi.chungbazi_be.domain.community.controller;

import chungbazi.chungbazi_be.domain.community.dto.CommunityRequestDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.service.CommunityService;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping(value = "/posts/upload", consumes = "multipart/form-data")
    @Operation(summary = "게시글 업로드 API",
            description = """
                커뮤니티 게시글 업로드 API
                - 카테고리 목록:
                    * JOBS: 일자리
                    * HOUSING: 주거
                    * EDUCATION: 교육
                    * WELFARE_CULTURE: 복지·문화
                    * PARTICIPATION_RIGHTS: 참여·권리
                """)
    public ApiResponse<CommunityResponseDTO.UploadAndGetPostDto> uploadPost(
            @RequestPart("info") @Valid CommunityRequestDTO.UploadPostDto uploadPostDto ,
            @RequestPart(value = "imageList", required = false) List<MultipartFile> imageList) {
        return ApiResponse.onSuccess(communityService.uploadPost(uploadPostDto, imageList));
    }

    @GetMapping(value = "/posts")
    @Operation(summary = "커뮤니티 전체 게시글 또는 카테고리별 게시글 조회 API",
            description = """
                커뮤니티 전체 게시글 또는 카테고리별 게시글 조회 API
                - 카테고리 목록:
                    * JOBS: 일자리
                    * HOUSING: 주거
                    * EDUCATION: 교육
                    * WELFARE_CULTURE: 복지·문화
                    * PARTICIPATION_RIGHTS: 참여·권리
                """)
    public ApiResponse<List<CommunityResponseDTO.PostListDto>> getPosts(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Long lastPostId,
            @RequestParam(defaultValue = "10") String size){
        int paseSize = Integer.parseInt(size);
        return ApiResponse.onSuccess(communityService.getPosts(category,lastPostId,paseSize));
    }

    @GetMapping(value = "/posts/{postId}")
    @Operation(summary = "개별 게시글 조회 API", description = "개별 게시글 조회 API")
    public ApiResponse<CommunityResponseDTO.UploadAndGetPostDto> getPost(
            @PathVariable Long postId) {
        return ApiResponse.onSuccess(communityService.getPost(postId));
    }

    @PostMapping(value = "/comments/upload")
    @Operation(summary = "댓글 업로드 API", description = "댓글 업로드 API")
    public ApiResponse<CommunityResponseDTO.UploadAndGetCommentDto> uploadComment(
            @RequestBody @Valid CommunityRequestDTO.UploadCommentDto uploadCommentDto){
        return ApiResponse.onSuccess(communityService.uploadComment(uploadCommentDto));
    }
}
