package chungbazi.chungbazi_be.domain.policy.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PolicyRecommendResponse {

    private List<PolicyListOneResponse> policies;
    private Set<Category> interests;
    private boolean isReadAllNotifications;
    private boolean hasNext;
    private Long nextCursor;

    public static PolicyRecommendResponse of(List<Policy> policies, Set<Category> interests, boolean hasNext,boolean isReadAllNotifications) {
        List<PolicyListOneResponse> response = policies.stream()
                .map(PolicyListOneResponse::from)
                .toList();

        Long nextCursor = hasNext ? policies.get(policies.size() - 1).getId() : null;

        return PolicyRecommendResponse.builder().policies(response).interests(interests).hasNext(hasNext)
                .isReadAllNotifications(isReadAllNotifications).nextCursor(nextCursor).build();
    }
}
