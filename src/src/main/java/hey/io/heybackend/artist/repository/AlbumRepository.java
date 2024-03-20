package hey.io.heybackend.artist.repository;

import hey.io.heybackend.artist.entities.Album;
import hey.io.heybackend.artist.entities.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findByArtist(Artist artist, Pageable pageable);

}
