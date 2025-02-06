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
import chungbazi.chungbazi_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserService userService;
    private final TokenGenerator tokenGenerator;
    private final AuthTokenService authTokenService;
    private final FCMTokenService fcmTokenService;
    private final KakaoAuthConverter kakaoAuthConverter;
    private final JwtProvider jwtProvider;


    // 로그인 및 회원가입 처리
    public TokenDTO loginUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        User user = userService.findOrCreateMember(request);
        boolean isFirst = userService.determineIsFirst(user);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        authTokenService.saveRefreshToken(user.getId(), tokenDTO.getRefreshToken(), tokenDTO.getRefreshExp());
        fcmTokenService.saveFcmToken(user.getId(), request.getFcmToken());
        return tokenDTO;
    }

    // JWT 토큰 관련 처리
    public TokenDTO recreateAccessToken(String refreshToken) {
        Long userId = jwtProvider.getUserIdFromToken(refreshToken);
        authTokenService.validateRefreshToken(userId, refreshToken);

        User user = userService.getUserById(userId);
        authTokenService.addToBlackList(refreshToken,"expired", 3600L);
        return tokenGenerator.generate(userId, user.getName(), false);
    }

    // 로그아웃
    public void logoutUser(String token) {
        authTokenService.validateNotBlackListed(token);
        Long userId = SecurityUtils.getUserId();
        authTokenService.addToBlackList(token, "logout", 3600L);
        authTokenService.deleteRefreshToken(userId);
    }

    // 회원 탈퇴
    public void deleteUserAccount(String token) {
        authTokenService.validateNotBlackListed(token);
        Long userId = SecurityUtils.getUserId();
        authTokenService.addToBlackList(token, "delete-account", 3600L);
        authTokenService.deleteRefreshToken(userId);
        userService.deleteUser(userId);
    }

    // 응답
    public TokenResponseDTO.LoginTokenResponseDTO createLoginTokenResponse(TokenDTO token) {
        return kakaoAuthConverter.toLoginTokenResponse(token);
    }

    public TokenResponseDTO.RefreshTokenResponseDTO createRefreshTokenResponse(TokenDTO token) {
        return kakaoAuthConverter.toRefreshTokenResponse(token);
    }


}