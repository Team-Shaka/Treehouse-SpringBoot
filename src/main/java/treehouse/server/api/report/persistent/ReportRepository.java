package treehouse.server.api.report.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
