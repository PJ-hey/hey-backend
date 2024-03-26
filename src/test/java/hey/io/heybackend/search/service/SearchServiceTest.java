package hey.io.heybackend.search.service;

import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.show.ShowInfoFixture;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private ShowRepository showRepository;

    @MockBean
    private ArtistRepository artistRepository;

    @Test
    void getSearch_success() {

        Show show = ShowInfoFixture.getShowInfo();
        Artist artist = ArtistInfoFixture.getArtistInfo();
        Pageable pageable = mock(Pageable.class);
        String keyword = "artist1";

        when(showRepository.getSearch(any(), eq(pageable))).thenReturn(Page.empty());
        when(artistRepository.getSearch(any(), eq(pageable))).thenReturn(Page.empty());

        assertDoesNotThrow(() -> searchService.getSearch(keyword, pageable));

    }
}
