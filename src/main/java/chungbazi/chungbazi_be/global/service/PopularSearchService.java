package chungbazi.chungbazi_be.global.service;

import chungbazi.chungbazi_be.domain.policy.dto.PopularSearchResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PopularSearchService {
    private final RedisTemplate<String, String> redisTemplate;
    public PopularSearchResponse getPopularSearch(String category) {

        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();  //Sorted Set을 다루기 위한 인터페이스
        String key = "ranking:" + category + ":" + LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        //상위 6개 검색어 반환
        Set<String> result = zSetOperations.reverseRange(key, 0, 5);
        List<String> resultList = result.stream().toList();
        return PopularSearchResponse.from(resultList);
    }
}
