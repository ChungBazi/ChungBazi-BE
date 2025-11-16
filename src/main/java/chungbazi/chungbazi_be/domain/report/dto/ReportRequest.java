package chungbazi.chungbazi_be.domain.report.dto;

import chungbazi.chungbazi_be.domain.report.entity.enums.ReportReason;
import lombok.Getter;

@Getter
public class ReportRequest {

    @Getter
    public static class ReportRequestDto{
        private ReportReason reason;
        private String description;
    }
}
