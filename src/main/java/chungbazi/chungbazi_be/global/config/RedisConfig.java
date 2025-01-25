package chungbazi.chungbazi_be.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories  // Spring Data Redis 리포지토리를 활성화(Redis를 사용한다고 명시해 주는 annotation)
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


    // yml에 저장한 host, post를 연결
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {  //RedisConnectionFactory : Redis Java 클라이언트 라이브러리인 Lettuce를 사용해서 Redis 서버와 연결해줌
        return new LettuceConnectionFactory(host, port);
    }

    // redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {  // RedisTemplate를 이용해 Redis의 데이터에 접근(RedisTemplate : Redis 데이터를 저장하고 조회하는 기능)
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());  // key 직렬화 방식 정의
        redisTemplate.setValueSerializer(new StringRedisSerializer());  // 저장되는 데이터값의 직렬화 방식을 정의
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }
}
