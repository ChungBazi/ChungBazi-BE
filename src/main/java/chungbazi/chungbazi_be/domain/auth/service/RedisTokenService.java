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
public class RedisTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveToken(String key, String token, long durationInSeconds) {
        redisTemplate.opsForValue().set(key, token, durationInSeconds, TimeUnit.SECONDS);
    }

    public String getToken(String key) {
        String token = redisTemplate.opsForValue().get(key);
        if (token == null) {
            throw new UnAuthorizedHandler(ErrorStatus.INVALID_OR_EXPIRED_REFRESH_TOKEN);
        }
        return token;
    }

    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
    public boolean isTokenExist(String key) {
        return redisTemplate.hasKey(key);
    }
}
