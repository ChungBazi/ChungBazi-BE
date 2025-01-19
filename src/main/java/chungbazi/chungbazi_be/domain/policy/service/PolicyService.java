package chungbazi.chungbazi_be.domain.policy.service;

import chungbazi.chungbazi_be.domain.policy.dto.YouthPolicyListResponse;
import chungbazi.chungbazi_be.domain.policy.dto.YouthPolicyResponse;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.policy.repository.PolicyRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PolicyService {

    private final WebClient webclient;
    private final PolicyRepository policyRepository;

    @Value("${webclient.openApiVlak}")
    private String openApiVlak;


    @Scheduled(cron = "0 20 23 * * *") // 매일 오후 12시 25분에 실행
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
            for (YouthPolicyResponse response : policies.getYouthPolicyList()) {
                if (policyRepository.existsByBizId(response.getBizId())) {
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
            YouthPolicyResponse lastPolicy = policies.getYouthPolicyList()
                    .get(policies.getYouthPolicyList().size() - 1);
            if (!isDateAvail(lastPolicy, twoMonthAgo)) {
                break;
            }

            pageIndex++;
        }

    }

    // XML -> DTO
    private YouthPolicyListResponse fetchPolicy(int display, int pageIndex, String srchPolyBizSecd) {

        String responseBody = webclient
                .get()
                .uri(uriBuilder -> {
                    return uriBuilder.path("") // 추가 경로 없이 기본 URL 그대로 사용
                            .queryParam("openApiVlak", openApiVlak) // 인증키
                            .queryParam("display", display) // 출력 건수
                            .queryParam("pageIndex", pageIndex) // 조회 페이지
                            .queryParam("srchPolyBizSecd", srchPolyBizSecd)
                            .build();
                })
                .retrieve()
                .bodyToMono(String.class)   // 서버 response content-type이 text/plain 라서
                .flux()
                .toStream()
                .findFirst()
                .orElse(null);

        // text/plain-> XML
        try {
            XmlMapper xmlMapper = new XmlMapper();

            return xmlMapper.readValue(responseBody, YouthPolicyListResponse.class); // XML 매핑
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
}
