package hey.io.heybackend.report.repository;

import hey.io.heybackend.report.entities.ShowReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowReportRepository extends JpaRepository<ShowReport, Long> {
}
