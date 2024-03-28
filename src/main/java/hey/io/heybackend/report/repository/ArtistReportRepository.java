package hey.io.heybackend.report.repository;

import hey.io.heybackend.report.entities.ArtistReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistReportRepository extends JpaRepository<ArtistReport, Long> {
}
