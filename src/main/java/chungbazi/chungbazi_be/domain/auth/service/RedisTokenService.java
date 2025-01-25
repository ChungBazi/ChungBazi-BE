package chungbazi.chungbazi_be.domain.auth.service;


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
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
    public boolean isTokenExist(String key) {
        return redisTemplate.hasKey(key);
    }
}
