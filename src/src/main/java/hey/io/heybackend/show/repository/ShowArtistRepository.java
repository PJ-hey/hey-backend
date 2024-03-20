package hey.io.heybackend.show.repository;

import hey.io.heybackend.show.entities.ShowArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowArtistRepository extends JpaRepository<ShowArtist, Long> {
}
