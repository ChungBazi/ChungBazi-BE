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
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final LoginTokenService loginTokenService;
    private final FCMTokenService fcmTokenService;
    private final KakaoAuthConverter kakaoAuthConverter;
    private final JwtProvider jwtProvider;


    // 로그인 및 회원가입 처리
    public TokenDTO loginUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        User user = findOrCreateMember(request);
        boolean isFirst = determineIsFirst(user);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        saveRefreshToken(user.getId(), tokenDTO);
        fcmTokenService.saveFcmToken(user.getId(), request.getFcmToken());
        return tokenDTO;
    }

    private User findOrCreateMember(TokenRequestDTO.LoginTokenRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .map(existingUser -> {
                    if (existingUser.isDeleted()) {
                        throw new BadRequestHandler(ErrorStatus.DEACTIVATED_ACCOUNT);
                    }
                    return existingUser;
                })
                .orElseGet(() -> createNewUser(request));
    }

    private User createNewUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();
        return userRepository.save(user);
    }

    private boolean determineIsFirst(User user) {
        return !user.isSurveyStatus();
    }

    // JWT 토큰 관련 처리
    public TokenDTO recreateAccessToken(String token) {
        Long userId = jwtProvider.getUserIdFromToken(token);
        loginTokenService.validateToken("REFRESH_TOKEN:"+userId, token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        addAccessTokenToBlacklist(token, "expired");
        return tokenGenerator.generate(userId, user.getName(), false);
    }

    public void logoutUser(String token) {
        validateTokenNotBlacklisted(token);
        Long userId = SecurityUtils.getUserId();
        addAccessTokenToBlacklist(token, "logout");
        removeRefreshToken(userId);
    }

    public void deleteUserAccount(String token) {
        validateTokenNotBlacklisted(token);
        Long userId = SecurityUtils.getUserId();
        addAccessTokenToBlacklist(token, "delete-account");
        removeRefreshToken(userId);
        userRepository.findById(userId).ifPresent(user -> {
            user.updateIsDeleted(true);
            userRepository.save(user);
        });
    }

    // 토큰 블랙리스트 및 검증 관련 처리
    private void validateTokenNotBlacklisted(String token) {
        if (loginTokenService.isTokenExist("BLACKLIST:"+token)) {
            String reason = loginTokenService.getToken("BLACKLIST:"+token);

            if ("blocked".equals(reason)) {
                throw new BadRequestHandler(ErrorStatus.BLOCKED_TOKEN);
            } else {
                throw new BadRequestHandler(ErrorStatus.EXPIRED_TOKEN);
            }
        }
    }

    private void addAccessTokenToBlacklist(String token, String reason) {
        loginTokenService.saveToken("BLACKLIST:" + token, reason, 3600L);
    }

    private void saveRefreshToken(Long userId, TokenDTO tokenDTO) {
        loginTokenService.saveToken("REFRESH_TOKEN:" + userId, tokenDTO.getRefreshToken(), tokenDTO.getRefreshExp());
    }
    private void removeRefreshToken(Long userId) {
        loginTokenService.deleteToken("REFRESH_TOKEN:" + userId);
    }

    // 응답
    public TokenResponseDTO.LoginTokenResponseDTO createLoginTokenResponse(TokenDTO token) {
        return kakaoAuthConverter.toLoginTokenResponse(token);
    }

    public TokenResponseDTO.RefreshTokenResponseDTO createRefreshTokenResponse(TokenDTO token) {
        return kakaoAuthConverter.toRefreshTokenResponse(token);
    }


}