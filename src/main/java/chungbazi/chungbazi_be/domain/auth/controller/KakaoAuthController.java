package chungbazi.chungbazi_be.domain.auth.controller;

import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenResponseDTO;
import chungbazi.chungbazi_be.domain.auth.jwt.CookieUtil;
import chungbazi.chungbazi_be.domain.auth.service.KakaoAuthService;
import chungbazi.chungbazi_be.domain.auth.service.RedisTokenService;
import chungbazi.chungbazi_be.domain.auth.service.TokenCookieService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.UnAuthorizedHandler;
import chungbazi.chungbazi_be.global.validation.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RedisTokenService redisTokenService;
    private final TokenCookieService tokenCookieService;

    @PostMapping("/kakao-login")
    @Operation(summary = "사용자 등록 및 JWT 생성 API", description = "사용자 정보를 받아서 DB에 저장하고 JWT 토큰을 생성")
    public ApiResponse<TokenResponseDTO> registerUser(@RequestBody TokenRequestDTO request, HttpServletResponse response) {

        TokenDTO token = kakaoAuthService.registerUser(request);
        tokenCookieService.addTokenCookies(response, token);

        return ApiResponse.onSuccess(kakaoAuthService.createTokenResponse(token));
    }
    @PostMapping("/refresh-token")
    @Operation(summary = "JWT 토큰 갱신 API", description = "refresh Token 으로 새로운 access Token 발급")
    public ApiResponse<TokenResponseDTO> refreshAccessToken(@AuthUser Long userId, HttpServletResponse response) {
        redisTokenService.getToken("REFRESH_TOKEN:" + userId);

        TokenDTO token = kakaoAuthService.refreshAccessToken(userId);
        tokenCookieService.addTokenCookies(response, token);

        return ApiResponse.onSuccess(kakaoAuthService.createTokenResponse(token));
    }

    @PostMapping("/kakao-logout")
    @Operation(summary = "로그아웃 API", description = "refresh Token 삭제하고 access Token 블랙리스트에 추가")
    public ApiResponse<String> logout(@AuthUser Long userId) {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        redisTokenService.saveToken("BLACKLIST:" + token, "logout", 3600L);
        redisTokenService.deleteToken("REFRESH_TOKEN:" + userId);

        return ApiResponse.onSuccess("Logout successful.");
    }
}