package chungbazi.chungbazi_be.domain.policy.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicySearchResponse {

    private List<PolicyListOneResponse> policies;
    private String nextCursor;
    private boolean hasNext;

    public static PolicySearchResponse of(List<PolicyListOneResponse> policy, String nextCursor, boolean hasNext) {
        return PolicySearchResponse.builder()
                .policies(policy)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}
