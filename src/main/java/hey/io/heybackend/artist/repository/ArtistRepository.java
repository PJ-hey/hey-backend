package hey.io.heybackend.artist.repository;

import hey.io.heybackend.artist.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>, ArtistRepositoryCustom{

    Optional<Artist> findByName(String name);

}
