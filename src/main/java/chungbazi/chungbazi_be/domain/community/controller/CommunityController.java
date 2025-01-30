package chungbazi.chungbazi_be.domain.community.controller;

import chungbazi.chungbazi_be.domain.community.dto.CommunityRequestDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.service.CommunityService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "게시글 업로드 API", description = "커뮤니티 게시글 업로드")
    public ApiResponse<CommunityResponseDTO.UploadPostDto> uploadPost(
            @RequestPart("info") @Valid CommunityRequestDTO.UploadPostDto uploadPostDto ,
            @RequestPart(value = "imageList", required = false) MultipartFile imageList) {
        return ApiResponse.onSuccess(communityService.uploadPost(uploadPostDto, imageList));
    }
}
