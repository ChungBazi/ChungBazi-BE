package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.dto.TokenResponseDTO;
import chungbazi.chungbazi_be.domain.auth.jwt.TokenGenerator;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.Gender;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.UnAuthorizedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final RedisTokenService redisTokenService;

    public TokenDTO registerUser(TokenRequestDTO request) {
        boolean isFirst = !userRepository.existsByEmail(request.getEmail());
        User user = findOrCreateMember(request);
        TokenDTO tokenDTO = tokenGenerator.generate(user.getId(), user.getName(), isFirst);
        redisTokenService.saveToken("REFRESH_TOKEN:" + user.getId(), tokenDTO.getRefreshToken(), tokenDTO.getAccessExp());

        return tokenDTO;
    }

    public TokenDTO refreshAccessToken(Long userId) {
        String storedRefreshToken = redisTokenService.getToken("REFRESH_TOKEN:" + userId);
        if (storedRefreshToken == null) {
            throw new UnAuthorizedHandler(ErrorStatus.INVALID_OR_EXPIRED_REFRESH_TOKEN);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        return tokenGenerator.generate(userId,user.getName(),false);
    }

    private User findOrCreateMember(TokenRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> newMember(request));
    }

    private User newMember(TokenRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .build();

        return userRepository.save(user);
    }

    public TokenResponseDTO createTokenResponse(TokenDTO token) {
        if (token.getUserId() == null || token.getUserName() == null || token.getIsFirst() == null) {
            throw new BadRequestHandler(ErrorStatus.INVALID_ARGUMENTS);
        }
        return TokenResponseDTO.of(token.getUserId(), token.getUserName(), token.getIsFirst());
    }
}