package chungbazi.chungbazi_be.domain.user.controller;

import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.service.UserService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "프로필 조회 API", description = "마이페이지 프로필 조회")
    public ApiResponse<UserResponseDTO.ProfileDto> getProfile() {
        return ApiResponse.onSuccess(userService.getProfile());
    }
    @PatchMapping(value = "/profile/update", consumes = "multipart/form-data")
    @Operation(summary = "프로필 수정 API", description = "마이페이지 프로필 수정")
    public ApiResponse<UserResponseDTO.ProfileUpdateDto> updateProfile(
            @RequestPart("info") @Valid UserRequestDTO.ProfileUpdateDto profileUpdateDto,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        return ApiResponse.onSuccess(userService.updateProfile(profileUpdateDto, profileImg));
    }

    @PostMapping("/register")
    @Operation(summary = "사용자 정보 등록 API", description = "사용자 정보(지역, 취업상태, 소득, 추가사항, 관심사, 학력) 등록")
    public ApiResponse<String> registerUserInfo(@Valid @RequestBody UserRequestDTO.RegisterDto registerDto) {
        userService.registerUserInfo(registerDto);
        return ApiResponse.onSuccess("User information registered successfully.");
    }

    @PatchMapping("/update")
    @Operation(summary = "사용자 정보 수정 API", description = "사용자 정보(지역, 취업상태, 소득, 추가사항, 관심사, 학력) 수정")
    public ApiResponse<String> updateUserInfo( @RequestBody UserRequestDTO.UpdateDto updateDto) {
        userService.updateUserInfo(updateDto);
        return ApiResponse.onSuccess("User information updated successfully.");
    }
}