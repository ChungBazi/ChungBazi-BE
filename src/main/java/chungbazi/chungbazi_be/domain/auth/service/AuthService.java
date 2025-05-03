package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.converter.AuthConverter;
import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenResponseDTO;
import chungbazi.chungbazi_be.domain.auth.jwt.JwtProvider;
import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.auth.jwt.TokenGenerator;
import chungbazi.chungbazi_be.domain.notification.service.FCMTokenService;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenGenerator tokenGenerator;
    private final TokenAuthService tokenAuthService;
    private final FCMTokenService fcmTokenService;
    private final AuthConverter authConverter;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 일반 회원가입
    public void registerUser(TokenRequestDTO.SignUpTokenRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestHandler(ErrorStatus.ALREADY_EXISTS_EMAIL);
        }
        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new BadRequestHandler(ErrorStatus.PASSWORD_MISMATCH);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .oAuthProvider(OAuthProvider.LOCAL)
                .build();
        userRepository.save(user);
    }

    // 일반 로그인
    public TokenDTO loginUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        User user = findUser(request);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestHandler(ErrorStatus.INVALID_CREDENTIALS);
        }
        boolean isFirst = determineIsFirst(user);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        tokenAuthService.saveRefreshToken(user.getId(), tokenDTO.getRefreshToken(), tokenDTO.getRefreshExp());
        fcmTokenService.saveFcmToken(user.getId(), request.getFcmToken());
        return tokenDTO;
    }

    // 소셜 로그인
    public TokenDTO socialLoginUser(TokenRequestDTO.SocialLoginTokenRequestDTO request, OAuthProvider oAuthProvider) {
        User user = findOrCreateUserForSocialLogin(request, oAuthProvider);
        boolean isFirst = determineIsFirst(user);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        tokenAuthService.saveRefreshToken(user.getId(), tokenDTO.getRefreshToken(), tokenDTO.getRefreshExp());
        fcmTokenService.saveFcmToken(user.getId(), request.getFcmToken());
        return tokenDTO;
    }

    // JWT 토큰 관련 처리
    public TokenDTO recreateAccessToken(String refreshToken) {
        Long userId = jwtProvider.getUserIdFromToken(refreshToken);
        tokenAuthService.validateRefreshToken(userId, refreshToken);

        User user = getUserById(userId);
        tokenAuthService.addToBlackList(refreshToken, "expired", 3600L);
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
        deleteUser(userId);
        fcmTokenService.deleteToken(Long.valueOf(userId));
    }

    // 응답
    public TokenResponseDTO.LoginTokenResponseDTO createLoginTokenResponse(TokenDTO token) {
        return authConverter.toLoginTokenResponse(token);
    }

    public TokenResponseDTO.RefreshTokenResponseDTO createRefreshTokenResponse(TokenDTO token) {
        return authConverter.toRefreshTokenResponse(token);
    }

    public User findUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .map(existingUser -> {
                    if (existingUser.isDeleted()) {
                        throw new BadRequestHandler(ErrorStatus.DEACTIVATED_ACCOUNT);
                    }
                    return existingUser;
                })
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
    }


    public User findOrCreateUserForSocialLogin(TokenRequestDTO.SocialLoginTokenRequestDTO request, OAuthProvider oAuthProvider) {
        return userRepository.findByEmail(request.getEmail())
                .map(existingUser -> {
                    if (existingUser.isDeleted()) {
                        throw new BadRequestHandler(ErrorStatus.DEACTIVATED_ACCOUNT);
                    }
                    return existingUser;
                })
                .orElseGet(() -> createUserForSocialLogin(request, oAuthProvider));
    }


    public User createUserForSocialLogin(TokenRequestDTO.SocialLoginTokenRequestDTO request, OAuthProvider oAuthProvider) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password("")
                .oAuthProvider(oAuthProvider)
                .build();
        return userRepository.save(user);
    }

    public boolean determineIsFirst(User user) {
        return !user.isSurveyStatus();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        // 유저 익명화 (username & email 무력화)
        userRepository.anonymizeUser(userId);
    }
}