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

    public static PolicyCalendarResponse from(Policy policy) {
        return PolicyCalendarResponse.builder()
                .name(policy.getName())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .build();
    }
}
