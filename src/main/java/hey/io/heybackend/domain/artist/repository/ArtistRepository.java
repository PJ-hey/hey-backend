package hey.io.heybackend.domain.artist.repository;

import hey.io.heybackend.domain.artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>, ArtistQueryRepository{


}
