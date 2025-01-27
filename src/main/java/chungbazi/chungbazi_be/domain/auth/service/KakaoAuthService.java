package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenResponseDTO;
import chungbazi.chungbazi_be.domain.auth.jwt.TokenGenerator;
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
    private final RedisTokenService redisTokenService;

    public TokenDTO loginUser(TokenRequestDTO request) {
        boolean isFirst = !userRepository.existsByEmail(request.getEmail());
        User user = findOrCreateMember(request);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        saveRefreshToken(user.getId(), tokenDTO);
        return tokenDTO;
    }

    public TokenDTO recreateAccessToken(Long userId) {
        redisTokenService.getToken("REFRESH_TOKEN:" + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        return tokenGenerator.generate(userId,user.getName(),false);
    }

    public void logoutUser(Long userId, String token) {
        addTokenToBlacklist(token, "logout");
        removeRefreshToken(userId);
    }

    public void deleteUserAccount(Long userId, String token) {
        addTokenToBlacklist(token, "delete-account");
        removeRefreshToken(userId);
        userRepository.findById(userId).ifPresent(user -> {
            user.updateIsDeleted(true);
            userRepository.save(user);
        });
    }

    private void addTokenToBlacklist(String token, String reason) {
        redisTokenService.saveToken("BLACKLIST:" + token, reason, 3600L);
    }

    private void saveRefreshToken(Long userId, TokenDTO tokenDTO) {
        redisTokenService.saveToken("REFRESH_TOKEN:" + userId, tokenDTO.getRefreshToken(), tokenDTO.getAccessExp());
    }
    private void removeRefreshToken(Long userId) {
        redisTokenService.deleteToken("REFRESH_TOKEN:" + userId);
    }


    private User findOrCreateMember(TokenRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> createNewUser(request));
    }

    private User createNewUser(TokenRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();

        return userRepository.save(user);
    }

    public TokenResponseDTO.LoginTokenResponseDTO createLoginTokenResponse(TokenDTO token) {
        if (token.getUserId() == null || token.getUserName() == null || token.getIsFirst() == null) {
            throw new BadRequestHandler(ErrorStatus.INVALID_ARGUMENTS);
        }
        return TokenResponseDTO.LoginTokenResponseDTO.of(
                token.getUserId(),
                token.getUserName(),
                token.getIsFirst(),
                token.getAccessToken(),
                token.getRefreshToken(),
                token.getAccessExp());
    }

    public TokenResponseDTO.RefreshTokenResponseDTO createRefreshTokenResponse(TokenDTO token) {
        return TokenResponseDTO.RefreshTokenResponseDTO.of(
                token.getAccessToken(),
                token.getAccessExp());
    }

}