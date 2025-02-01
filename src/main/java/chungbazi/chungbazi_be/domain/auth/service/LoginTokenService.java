package chungbazi.chungbazi_be.domain.auth.service;


import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.UnAuthorizedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveToken(String key, String value, long durationInSeconds) {
        redisTemplate.opsForValue().set(key, value, durationInSeconds, TimeUnit.SECONDS);
    }

    public String getToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
    public boolean isTokenExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    public void validateToken(String key, String refreshToken) {
        String storedRefreshToken = getToken(key);
        if (storedRefreshToken == null) {
            throw new UnAuthorizedHandler(ErrorStatus.NOT_FOUND_TOKEN);
        }
        if (!storedRefreshToken.equals(refreshToken)) {
            throw new UnAuthorizedHandler(ErrorStatus.INVALID_TOKEN);
        }
    }
}
