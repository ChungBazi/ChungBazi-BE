package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.converter.KakaoAuthConverter;
import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenResponseDTO;
import chungbazi.chungbazi_be.domain.auth.jwt.JwtProvider;
import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.auth.jwt.TokenGenerator;
import chungbazi.chungbazi_be.domain.notification.service.FCMTokenService;
import chungbazi.chungbazi_be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserAuthService userAuthService;
    private final TokenGenerator tokenGenerator;
    private final TokenAuthService tokenAuthService;
    private final FCMTokenService fcmTokenService;
    private final KakaoAuthConverter kakaoAuthConverter;
    private final JwtProvider jwtProvider;


    // 로그인 및 회원가입 처리
    public TokenDTO loginUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        User user = userAuthService.findOrCreateMember(request);
        boolean isFirst = userAuthService.determineIsFirst(user);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        tokenAuthService.saveRefreshToken(user.getId(), tokenDTO.getRefreshToken(), tokenDTO.getRefreshExp());
        fcmTokenService.saveFcmToken(user.getId(), request.getFcmToken());
        return tokenDTO;
    }

    // JWT 토큰 관련 처리
    public TokenDTO recreateAccessToken(String refreshToken) {
        Long userId = jwtProvider.getUserIdFromToken(refreshToken);
        tokenAuthService.validateRefreshToken(userId, refreshToken);

        User user = userAuthService.getUserById(userId);
        tokenAuthService.addToBlackList(refreshToken,"expired", 3600L);
        return tokenGenerator.generate(userId, user.getName(), false);
    }

    // 로그아웃
    public void logoutUser(String token) {
        tokenAuthService.validateNotBlackListed(token);
        Long userId = SecurityUtils.getUserId();
        tokenAuthService.addToBlackList(token, "logout", 3600L);
        tokenAuthService.deleteRefreshToken(userId);
    }

    // 회원 탈퇴
    public void deleteUserAccount(String token) {
        tokenAuthService.validateNotBlackListed(token);
        Long userId = SecurityUtils.getUserId();
        tokenAuthService.addToBlackList(token, "delete-account", 3600L);
        tokenAuthService.deleteRefreshToken(userId);
        userAuthService.deleteUser(userId);
        fcmTokenService.deleteToken(Long.valueOf(userId));
    }

    // 응답
    public TokenResponseDTO.LoginTokenResponseDTO createLoginTokenResponse(TokenDTO token) {
        return kakaoAuthConverter.toLoginTokenResponse(token);
    }

    public TokenResponseDTO.RefreshTokenResponseDTO createRefreshTokenResponse(TokenDTO token) {
        return kakaoAuthConverter.toRefreshTokenResponse(token);
    }


}