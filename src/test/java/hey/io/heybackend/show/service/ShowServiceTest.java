package hey.io.heybackend.show.service;

import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.show.ShowInfoFixture;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.entities.ShowArtist;
import hey.io.heybackend.show.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShowServiceTest {

    @Autowired
    private ShowService showService;

    @MockBean
    private ShowRepository showRepository;

    @MockBean
    private ArtistRepository artistRepository;

    @Test
    void createShow_success() {

        CreateShowRequest request = ShowInfoFixture.getCreateRequestInfo();
        Artist artist = ArtistInfoFixture.getArtistInfo();
        when(showRepository.save(any())).thenReturn(mock(Show.class));
        when(artistRepository.findByName(any())).thenReturn(Optional.of(artist));
        assertDoesNotThrow(() -> showService.createShow(request));

    }

    @Test
    void getShow_success() {

        Pageable pageable = mock(Pageable.class);
        String type = "concert";
        String genre = "hiphop";


        when(showRepository.getList(eq(pageable), eq(type), eq(genre))).thenReturn(Page.empty());
        assertDoesNotThrow(() -> showService.getShow(pageable, type, genre));

    }

    @Test
    void getShowInfo_success() {

        Show show = ShowInfoFixture.getShowInfo();
        when(showRepository.findById(any())).thenReturn(Optional.of(show));
        assertDoesNotThrow(() -> showService.getShowInfo(any()));

    }

    @Test
    void getShowInfo_showNotFound() {

        Show show = ShowInfoFixture.getShowInfo();
        when(showRepository.findById(any())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> showService.getShowInfo(any()));
        assertEquals(ErrorCode.SHOW_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void updateShow_showNotFound() {

        Show show = ShowInfoFixture.getShowInfo();
        UpdateShowRequest request = ShowInfoFixture.getUpdateRequestInfo();
        when(showRepository.findById(show.getId())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> showService.updateShow(show.getId(), request));
        assertEquals(ErrorCode.SHOW_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void deleteShow_showNotFound() {

        Show show = ShowInfoFixture.getShowInfo();
        when(showRepository.findById(show.getId())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> showService.deleteShow(show.getId()));
        assertEquals(ErrorCode.SHOW_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void getShowArtist_showNotFound() {

        Show show = ShowInfoFixture.getShowInfo();
        when(showRepository.findById(show.getId())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> showService.getShowArtist(show.getId()));
        assertEquals(ErrorCode.SHOW_NOT_FOUND, exception.getErrorCode());

    }
}
