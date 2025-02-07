package chungbazi.chungbazi_be.domain.auth.service;


import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.UnAuthorizedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenAuthService {

    private final RedisTemplate<String, String> redisTemplate;

    //Refresh Token
    public void saveRefreshToken(Long userId, String refreshToken, long duration) {
        redisTemplate.opsForValue().set("REFRESH_TOKEN:" + userId, refreshToken, duration, TimeUnit.SECONDS);
    }

    public String getRefreshToken(Long userId) {
        return redisTemplate.opsForValue().get("REFRESH_TOKEN:" + userId);
    }

    public void deleteRefreshToken(Long userId) {
        redisTemplate.delete("REFRESH_TOKEN:" + userId);
    }

    public boolean isRefreshTokenExist(Long userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("REFRESH_TOKEN:" + userId));
    }

    public void validateRefreshToken(Long userId, String token) {
        String storedToken = getRefreshToken(userId);
        if (storedToken == null) {
            throw new UnAuthorizedHandler(ErrorStatus.NOT_FOUND_TOKEN);
        }
        if (!storedToken.equals(token)) {
            throw new UnAuthorizedHandler(ErrorStatus.INVALID_TOKEN);
        }
    }

    //BlackList
    public void addToBlackList(String token, String reason, long duration) {
        redisTemplate.opsForValue().set("BLACKLIST:" + token, reason, duration, TimeUnit.SECONDS);
    }

    public boolean isBlackListed(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("BLACKLIST:" + token));
    }

    public String getBlackListReason(String token) {
        return redisTemplate.opsForValue().get("BLACKLIST:" + token);
    }

    public void validateNotBlackListed(String token) {
        if (isBlackListed(token)) {
            throw new BadRequestHandler(ErrorStatus.BLOCKED_TOKEN);
        }
    }
}

