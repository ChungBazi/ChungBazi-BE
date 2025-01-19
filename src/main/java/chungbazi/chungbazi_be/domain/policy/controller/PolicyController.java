package chungbazi.chungbazi_be.domain.policy.controller;

import chungbazi.chungbazi_be.domain.policy.dto.PolicySearchResponse;
import chungbazi.chungbazi_be.domain.policy.dto.PopularSearchResponse;
import chungbazi.chungbazi_be.domain.policy.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/search/popular")
    public ResponseEntity<PopularSearchResponse> getPopularSearch() {

        PopularSearchResponse response = policyService.getPopularSearch();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/policy")
    public ResponseEntity<PolicySearchResponse> getSearchPolicy(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "cursor", required = false) String cursor,
            @RequestParam(value = "size", defaultValue = "15", required = false) int size) {

        PolicySearchResponse response = policyService.getSearchPolicy(name, cursor, size);
        return ResponseEntity.ok(response);
    }

}
