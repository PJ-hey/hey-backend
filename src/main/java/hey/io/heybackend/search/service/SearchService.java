package hey.io.heybackend.search.service;

import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.search.dtos.SearchResponse;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final ShowRepository showRepository;
    private final ArtistRepository artistRepository;

    public Page<SearchResponse> getSearch(String keyword, Pageable pageable) {

        Page<Show> shows = showRepository.getSearch(keyword, pageable);
        Page<Artist> artists = artistRepository.getSearch(keyword, pageable);

        List<SearchResponse> showResponse = shows.getContent().stream()
                .map(SearchResponse::fromShow)
                .collect(Collectors.toList());

        List<SearchResponse> artistResponse = artists.getContent().stream()
                .map(SearchResponse::fromArtist)
                .collect(Collectors.toList());

        List<SearchResponse> searchResponse = Stream.concat(showResponse.stream(), artistResponse.stream())
                .collect(Collectors.toList());

        return new PageImpl<>(searchResponse, pageable, searchResponse.size());
    }
}
