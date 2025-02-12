package chungbazi.chungbazi_be.domain.policy.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.cart.entity.Cart;
import chungbazi.chungbazi_be.domain.cart.service.CartService;
import chungbazi.chungbazi_be.domain.document.entity.CalendarDocument;
import chungbazi.chungbazi_be.domain.document.service.CalendarDocumentService;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyCalendarDetailResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyCalendarResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyDetailsResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyListOneResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyListResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PopularSearchResponse;
import chungbazi.chungbazi_be.domain.policy.dto.YouthPolicyListResponse;
import chungbazi.chungbazi_be.domain.policy.dto.YouthPolicyResponse;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.policy.entity.QPolicy;
import chungbazi.chungbazi_be.domain.policy.repository.PolicyRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PolicyService {

    private final WebClient webclient;
    private final PolicyRepository policyRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final CartService cartService;
    private final CalendarDocumentService calendarDocumentService;


    @Value("${webclient.openApiVlak}")
    private String openApiVlak;

    /*
        @Scheduled(cron = "0 40 18 * * *") // 매일 오전 3시 20분에 실행
        @Transactional
        public void schedulePolicyFetch() {
            getPolicy();
        }

        // OpenAPI에서 정책 가져오기
        @Transactional
        public void getPolicy() {

            int display = 20;
            int pageIndex = 1;
            String srchPolyBizSecd = "003002001";

            LocalDate twoMonthAgo = LocalDate.now().minusMonths(2);

            while (true) {
                // XML -> DTO
                YouthPolicyListResponse policies = fetchPolicy(display, pageIndex, srchPolyBizSecd);

                if (policies == null) {
                    break;
                }

                // DB에 이미 존재하는 bizId가 있는지 확인 & 날짜 유효한 것만 DTO -> Entity
                List<Policy> validPolicies = new ArrayList<>();
                for (YouthPolicyResponse response : policies.getResult().getYouthPolicyList()) {
                    if (policyRepository.existsByBizId(response.getPlcyNo())) {
                        break;
                    }
                    if (isDateAvail(response, twoMonthAgo)) {
                        validPolicies.add(Policy.toEntity(response));
                    }
                }
                if (!validPolicies.isEmpty()) {
                    policyRepository.saveAll(validPolicies);
                }

                // 마지막 정책 마감날짜
                YouthPolicyResponse lastPolicy = policies.getResult().getYouthPolicyList()
                        .get(policies.getResult().getYouthPolicyList().size() - 1);
                if (!isDateAvail(lastPolicy, twoMonthAgo)) {
                    break;
                }

                pageIndex++;
            }

        }

    */
    // 정책 검색
    public PolicyListResponse getSearchPolicy(String name, String cursor, int size, String order) {

        if (name == null) {
            throw new GeneralException(ErrorStatus.NO_SEARCH_NAME);
        }

        String normalizedName = normalizeSearchItem(name);

        // 인기 검색어에 반영
        updatePopularSearch(normalizedName);

        // 검색 결과 반환
        List<Tuple> policies = policyRepository.searchPolicyWithName(name, cursor, size + 1, order);

        String nextCursor = null;
        boolean hasNext = policies.size() > size;

        if (hasNext) {
            policies.subList(0, size);

            Tuple lastTuple = policies.get(policies.size() - 1);
            nextCursor = policyRepository.generateNextCursor(lastTuple, name);
        }

        List<PolicyListOneResponse> policyDtoList = new ArrayList<>();
        for (Tuple tuple : policies) {
            Policy policy = tuple.get(QPolicy.policy);
            policyDtoList.add(PolicyListOneResponse.from(policy));
        }

        if (policies.isEmpty()) {
            return PolicyListResponse.of(policyDtoList, null, false);
        }

        return PolicyListResponse.of(policyDtoList, nextCursor, hasNext);
    }


    // 인기 검색어 조회
    public PopularSearchResponse getPopularSearch() {

        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();  //Sorted Set을 다루기 위한 인터페이스
        String key = "ranking:" + LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        //상위 6개 검색어 반환
        Set<String> result = zSetOperations.reverseRange(key, 0, 5);
        List<String> resultList = result.stream().toList();
        return PopularSearchResponse.from(resultList);

    }


    // 카테고리별 정책 조회
    public PolicyListResponse getCategoryPolicy(Category categoryName, Long cursor, int size, String order) {

        List<Policy> policies = policyRepository.getPolicyWithCategory(categoryName, cursor, size + 1, order);

        boolean hasNext = policies.size() > size;

        if (hasNext) {
            policies.subList(0, size);
        }

        List<PolicyListOneResponse> policyDtoList = policies.stream().map(PolicyListOneResponse::from).toList();

        return PolicyListResponse.of(policyDtoList, hasNext);
    }

    // 정책상세조회
    public PolicyDetailsResponse getPolicyDetails(Long policyId) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.POLICY_NOT_FOUND));
        return PolicyDetailsResponse.from(policy);
    }

    // JSON -> DTO
    private YouthPolicyListResponse fetchPolicy(int display, int pageIndex, String srchPolyBizSecd) {

        String responseBody = webclient
                .get()
                .uri(uriBuilder -> {
                    return uriBuilder.path("") // 추가 경로 없이 기본 URL 그대로 사용
                            .queryParam("apiKeyNum", openApiVlak) // 인증키
                            .queryParam("pageSize", display) // 출력 건수
                            .queryParam("pageNum", pageIndex) // 조회 페이지
                            //.queryParam("srchPolyBizSecd", srchPolyBizSecd)
                            .build();
                })
                .retrieve()
                .bodyToMono(String.class)   // 서버 response content-type이 text/plain 라서
                .flux()
                .toStream()
                .findFirst()
                .orElse(null);

        // text/plain-> JSON
        try {
            //Json 문자열을 자바 객체에 매핑해주는 역할
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON -> DTO 매핑
            return objectMapper.readValue(responseBody, YouthPolicyListResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    // 날짜 나와있는지 + 2달 이내 정책인지 검증
    private boolean isDateAvail(YouthPolicyResponse response, LocalDate twoMonthAgo) {

        LocalDate endDate = response.getEndDate();

        // 날짜 안 나옴
        if (endDate == null) {
            return false;
        }
        // 2달 이내 정책인지
        if (endDate.isAfter(twoMonthAgo)) {
            return true;
        }

        return false;
    }


    // 띄어쓰기, 대소문자 구분 X
    private String normalizeSearchItem(String searchItem) {

        //소문자 변환
        String normalized = searchItem.toLowerCase();

        //띄어쓰기 제거
        normalized = normalized.replaceAll("\\s+", " ");

        return normalized;
    }


    // 인기 검색어에 반영
    private void updatePopularSearch(String normalizedName) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();  //Sorted Set을 다루기 위한 인터페이스

        // 인기 검색어에 반영 (오늘 포함 3일치 key에 저장, 오늘+1/내일+0.6/내일모레+0.3
        for (int i = 0; i < 3; i++) {

            String key = "ranking:" + LocalDate.now().plusDays(i).format(DateTimeFormatter.ISO_DATE);

            // 키 존재 여부 확인, TTL 설정
            boolean keyExists = Boolean.TRUE.equals(redisTemplate.hasKey(key));
            if (!keyExists) {
                redisTemplate.expire(key, 3, TimeUnit.DAYS);
            }

            Double cnt = zSetOperations.score(key, normalizedName); // score는 double 타입을 반환

            double num = switch (i) {
                case 0 -> 1;
                case 1 -> 0.6;
                case 2 -> 0.3;
                default -> 0;
            };

            if (cnt == null) {
                zSetOperations.add(key, normalizedName, num);
            } else {
                zSetOperations.add(key, normalizedName, cnt + num);
            }
        }
    }

    public Policy findByPolicyId(Long policyId) {
        return policyRepository.findById(policyId).orElseThrow(() -> new NotFoundHandler(ErrorStatus.POLICY_NOT_FOUND));
    }

    // 캘린더 정책 전체 조회
    public List<PolicyCalendarResponse> getCalendarList(String yearMonth) {

        //유효한 타입인지 검증
        validateYearMonth(yearMonth);

        YearMonth parsedYearMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-M"));
        int year = parsedYearMonth.getYear();
        int month = parsedYearMonth.getMonthValue();

        Long userId = SecurityUtils.getUserId();
        return cartService.findByUser_IdAndYearMonth(userId, year, month);
    }

    // 유효한 타입인지 확인
    private void validateYearMonth(String yearMonth) {
        try {
            YearMonth.parse(yearMonth); // "2025-01" 형식이 아닌 경우 예외 발생
        } catch (DateTimeParseException e) {
            // 유효하지 않은 형식인 경우 CustomException 던지기
            throw new BadRequestHandler(ErrorStatus.NOT_VALID_TYPE_YEAR_MONTH);
        }
    }

    public PolicyCalendarDetailResponse getCalendarDetail(Long cartId) {

        Cart cart = cartService.findById(cartId);
        Policy policy = cart.getPolicy();
        List<CalendarDocument> documents = calendarDocumentService.findAllByCart_Id(cartId);

        return PolicyCalendarDetailResponse.of(cart, policy, documents);
    }
}