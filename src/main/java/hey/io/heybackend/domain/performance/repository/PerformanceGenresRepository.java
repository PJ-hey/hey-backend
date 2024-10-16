package hey.io.heybackend.domain.performance.repository;

import hey.io.heybackend.domain.performance.entity.PerformanceGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceGenresRepository extends JpaRepository<PerformanceGenres, Long> {
}