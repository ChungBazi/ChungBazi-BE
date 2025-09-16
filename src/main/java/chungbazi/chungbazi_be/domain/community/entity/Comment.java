package chungbazi.chungbazi_be.domain.community.entity;

import chungbazi.chungbazi_be.domain.report.entity.enums.ReportReason;
import chungbazi.chungbazi_be.global.utils.TimeFormatter;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(columnDefinition = "integer default 0")
    private Integer reportCount = 0;

    @Column(name = "report_reason")
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    public void increaseReportCount() {
        this.reportCount++;
    }

    public void delete(ReportReason reason) {
        this.reportReason = reason;
    }

    public String getFormattedCreatedAt() {
        return TimeFormatter.formatCreatedAt(this.getCreatedAt());
    }
}
