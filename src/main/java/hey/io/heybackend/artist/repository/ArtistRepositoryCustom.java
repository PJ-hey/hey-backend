package hey.io.heybackend.artist.repository;

import hey.io.heybackend.artist.entities.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistRepositoryCustom {

    Page<Artist> getSearch(String keyword, Pageable pageable);

}
