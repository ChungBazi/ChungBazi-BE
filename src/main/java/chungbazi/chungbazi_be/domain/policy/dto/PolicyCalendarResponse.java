package chungbazi.chungbazi_be.domain.policy.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyCalendarResponse {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;
    private Long cartId;
    private Long policyId;

    public static PolicyCalendarResponse of(Policy policy, Long cartId) {
        return PolicyCalendarResponse.builder()
                .name(policy.getName())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .cartId(cartId)
                .policyId(policy.getId())
                .build();
    }
}
