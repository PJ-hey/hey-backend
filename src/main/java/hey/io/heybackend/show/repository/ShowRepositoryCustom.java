package hey.io.heybackend.show.repository;

import hey.io.heybackend.show.entities.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShowRepositoryCustom {

    Page<Show> getList(Pageable pageable, String type, String genre);

    Page<Show> getSearch(String keyword, Pageable pageable);

    Page<Show> findShowsByArtistId(Long artistId, Pageable pageable);
}
