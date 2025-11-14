package chungbazi.chungbazi_be.domain.report.entity;

import chungbazi.chungbazi_be.domain.report.entity.enums.ReportReason;
import chungbazi.chungbazi_be.domain.report.entity.enums.ReportType;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(
//        name = "report",
//        indexes = {
//                @Index(name = "idx_report_reporter_type_target", columnList = "reporter_id, report_type, target_id"),
//                @Index(name = "idx_report_reporter_type",       columnList = "reporter_id, report_type"),
//                @Index(name = "idx_report_target_type",          columnList = "target_id, report_type")
//        }
//) 추후에 데이터 많아지면 반영
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(length = 200)
    private String description;

    @Column(name="target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    public boolean isOtherReason() {
        return this.reportReason == ReportReason.OTHER;
    }
}
