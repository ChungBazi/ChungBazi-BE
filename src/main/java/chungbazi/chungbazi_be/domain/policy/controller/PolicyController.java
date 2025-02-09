package chungbazi.chungbazi_be.domain.policy.controller;

import chungbazi.chungbazi_be.domain.policy.dto.PolicyCalendarResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyDetailsResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PolicyListResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PopularSearchResponse;
import chungbazi.chungbazi_be.domain.policy.service.PolicyService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyService policyService;

    // 정책 검색
    @GetMapping("/search")
    public ApiResponse<PolicyListResponse> getSearchPolicy(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "cursor", required = false) String cursor,
            @RequestParam(value = "size", defaultValue = "15", required = false) int size,
            @RequestParam(value = "order", defaultValue = "latest", required = false) String order) {

        PolicyListResponse response = policyService.getSearchPolicy(name, cursor, size, order);
        return ApiResponse.onSuccess(response);
    }

    // 인기 검색어 조회
    @GetMapping("/search/popular")
    public ApiResponse<PopularSearchResponse> getPopularSearch() {

        PopularSearchResponse response = policyService.getPopularSearch();
        return ApiResponse.onSuccess(response);
    }

    // 카테고리별 정책 검색
    @GetMapping
    public ApiResponse<PolicyListResponse> getCategoryPolicy(
            @RequestParam(value = "category", required = true) String category,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "size", defaultValue = "15", required = false) int size,
            @RequestParam(value = "order", defaultValue = "latest", required = false) String order) {

        PolicyListResponse response = policyService.getCategoryPolicy(category, cursor, size, order);
        return ApiResponse.onSuccess(response);
    }


    // 정책 상세 조회
    @GetMapping("/{policyId}")
    public ApiResponse<PolicyDetailsResponse> getPolicyDetails(@PathVariable Long policyId) {

        PolicyDetailsResponse response = policyService.getPolicyDetails(policyId);
        return ApiResponse.onSuccess(response);
    }

    // 캘린더 정책 전체 조회
    @GetMapping("/calendar")
    public ApiResponse<List<PolicyCalendarResponse>> getCalendarList(@RequestParam String yearMonth) {

        List<PolicyCalendarResponse> response = policyService.getCalendarList(yearMonth);
        return ApiResponse.onSuccess(response);
    }

}