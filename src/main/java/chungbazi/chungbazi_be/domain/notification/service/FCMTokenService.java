package chungbazi.chungbazi_be.domain.notification.service;

import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    public void saveFcmToken(Long userId, String fcmToken) {
        redisTemplate.opsForValue().set("_"+String.valueOf(userId), fcmToken);
    }

    public String getToken(Long userId){
        return redisTemplate.opsForValue().get("_"+String.valueOf(userId));
    }

    public void deleteToken(Long userId){
        redisTemplate.delete(String.valueOf("_"+userId));
    }



}
