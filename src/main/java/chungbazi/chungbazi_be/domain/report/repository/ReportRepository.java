package chungbazi.chungbazi_be.domain.report.repository;

import chungbazi.chungbazi_be.domain.report.entity.Report;
import chungbazi.chungbazi_be.domain.report.entity.enums.ReportType;
import chungbazi.chungbazi_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByReporterAndReportTypeAndTargetId(User reporter, ReportType reportType, Long targetId);
}
