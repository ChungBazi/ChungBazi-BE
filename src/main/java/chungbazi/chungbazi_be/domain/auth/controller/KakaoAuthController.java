package chungbazi.chungbazi_be.domain.auth.controller;

import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenResponseDTO;
import chungbazi.chungbazi_be.domain.auth.service.KakaoAuthService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/kakao-login")
    @Operation(summary = "사용자 등록 및 JWT 생성 API", description = "사용자 정보를 받아서 DB에 저장하고 JWT 토큰을 생성")
    public ApiResponse<TokenResponseDTO.LoginTokenResponseDTO> registerUser(@Valid @RequestBody TokenRequestDTO.LoginTokenRequestDTO request) {
        TokenDTO token = kakaoAuthService.loginUser(request);
        return ApiResponse.onSuccess(kakaoAuthService.createLoginTokenResponse(token));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "JWT 토큰 갱신 API", description = "refresh Token 으로 새로운 access Token 발급")
    public ApiResponse<TokenResponseDTO.RefreshTokenResponseDTO> refreshAccessToken(@RequestBody TokenRequestDTO.refreshTokenRequestDTO request) {
        TokenDTO newToken = kakaoAuthService.recreateAccessToken(request.getRefreshToken());
        return ApiResponse.onSuccess(kakaoAuthService.createRefreshTokenResponse(newToken));
    }

    @PostMapping("/kakao-logout")
    @Operation(summary = "로그아웃 API", description = "refresh Token 삭제하고 access Token 블랙리스트에 추가")
    public ApiResponse<String> logout() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        kakaoAuthService.logoutUser(token);
        return ApiResponse.onSuccess("Logout successful.");
    }

    @PostMapping("/delete-account")
    @Operation(summary = "회원 탈퇴 API", description = "access Token을 블랙리스트에 추가하고 refresh Token 삭제, 회원 정보 삭제")
    public ApiResponse<String> deleteAccount() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        kakaoAuthService.deleteUserAccount(token);
        return ApiResponse.onSuccess("Account deletion successful.");
    }
}