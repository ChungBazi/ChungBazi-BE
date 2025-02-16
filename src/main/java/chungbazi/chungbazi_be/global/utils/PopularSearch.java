package chungbazi.chungbazi_be.global.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularSearch {
    private final RedisTemplate<String, String> redisTemplate;
    // 띄어쓰기, 대소문자 구분 X
    private String normalizeSearchItem(String searchItem) {

        //소문자 변환
        String normalized = searchItem.toLowerCase();

        //띄어쓰기 제거
        normalized = normalized.replaceAll("\\s+", " ");

        return normalized;
    }

    // 인기 검색어에 반영
    public void updatePopularSearch(String query, String category) {
        String normalizedQuery = normalizeSearchItem(query);
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();  //Sorted Set을 다루기 위한 인터페이스

        // 인기 검색어에 반영 (오늘 포함 3일치 key에 저장, 오늘+1/내일+0.6/내일모레+0.3
        for (int i = 0; i < 3; i++) {
            String key = "ranking:" + category + ":" + LocalDate.now().plusDays(i).format(DateTimeFormatter.ISO_DATE);

            // 키 존재 여부 확인, TTL 설정
            boolean keyExists = Boolean.TRUE.equals(redisTemplate.hasKey(key));
            if (!keyExists) {
                redisTemplate.expire(key, 3, TimeUnit.DAYS);
            }

            Double cnt = zSetOperations.score(key, normalizedQuery); // score는 double 타입을 반환

            double num = switch (i) {
                case 0 -> 1;
                case 1 -> 0.6;
                case 2 -> 0.3;
                default -> 0;
            };

            if (cnt == null) {
                zSetOperations.add(key, normalizedQuery, num);
            } else {
                zSetOperations.add(key, normalizedQuery, cnt + num);
            }
        }
    }
}
