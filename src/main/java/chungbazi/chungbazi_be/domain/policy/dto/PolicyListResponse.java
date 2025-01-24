package chungbazi.chungbazi_be.domain.policy.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyListResponse {

    private List<PolicyListOneResponse> policies;
    private String nextCursor;
    private boolean hasNext;

    
    public static PolicyListResponse of(List<PolicyListOneResponse> policy, String nextCursor, boolean hasNext) {
        return PolicyListResponse.builder()
                .policies(policy)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    public static PolicyListResponse of(List<PolicyListOneResponse> policy, boolean hasNext) {
        return PolicyListResponse.builder()
                .policies(policy)
                .hasNext(hasNext)
                .build();
    }
}
