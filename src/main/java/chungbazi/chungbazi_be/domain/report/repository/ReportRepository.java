package chungbazi.chungbazi_be.domain.report.repository;

import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.report.entity.Report;
import chungbazi.chungbazi_be.domain.report.entity.enums.ReportType;
import chungbazi.chungbazi_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByReporterAndReportTypeAndTargetId(User reporter, ReportType reportType, Long targetId);

    @Query("SELECT r.targetId FROM Report r WHERE r.reporter.id = :reporterId AND r.reportType = :reportType")
    List<Long> findReportedTargetIdsByReporterAndType(@Param("reporterId") Long reporterId, @Param("reportType") ReportType reportType);


    Report findByTargetIdAndReportType(Long targetId, ReportType reportType);
}
