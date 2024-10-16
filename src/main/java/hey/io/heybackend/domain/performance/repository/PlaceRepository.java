package hey.io.heybackend.domain.performance.repository;

import hey.io.heybackend.domain.performance.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
