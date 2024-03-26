package hey.io.heybackend.artist.service;

import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.dtos.request.CreateArtistRequest;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.AlbumRepository;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.show.repository.ShowArtistRepository;
import hey.io.heybackend.show.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArtistServiceTest {

    @Autowired
    private ArtistService artistService;

    @MockBean
    private ArtistRepository artistRepository;

    @MockBean
    private AlbumRepository albumRepository;

    @MockBean
    private ShowRepository showRepository;


    @Test
    void createArtist_success() {

        CreateArtistRequest request = ArtistInfoFixture.getCreateRequestInfo();
        when(artistRepository.save(any())).thenReturn(mock(Artist.class));
        assertDoesNotThrow(() -> artistService.createArtist(request));

    }

    @Test
    void getArtistInfo_success() {

        Artist artist = ArtistInfoFixture.getArtistInfo();
        when(artistRepository.findById(any())).thenReturn(Optional.of(artist));
        assertDoesNotThrow(() -> artistService.getArtistInfo(any()));

    }

    @Test
    void getArtistInfo_artistNotFound() {

        Artist artist = ArtistInfoFixture.getArtistInfo();
        when(artistRepository.findById(any())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> artistService.getArtistInfo(any()));
        assertEquals(ErrorCode.ARTIST_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void updateArtist_artistNotFound() {

        Artist artist = ArtistInfoFixture.getArtistInfo();
        UpdateArtistRequest request = ArtistInfoFixture.getUpdateRequestInfo();
        when(artistRepository.findById(artist.getId())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> artistService.updateArtist(artist.getId(), request));
        assertEquals(ErrorCode.ARTIST_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void deleteArtist_artistNotFound() {

        Artist artist = ArtistInfoFixture.getArtistInfo();
        when(artistRepository.findById(artist.getId())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> artistService.deleteArtist(artist.getId()));
        assertEquals(ErrorCode.ARTIST_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void getAlbums_success() {

        Pageable pageable = mock(Pageable.class);
        Artist artist = ArtistInfoFixture.getArtistInfo();

        when(artistRepository.findById(any())).thenReturn(Optional.of(artist));
        when(albumRepository.findByArtist(any(), any())).thenReturn(Page.empty());

        assertDoesNotThrow(() -> artistService.getAlbums(any(), pageable));

    }

    @Test
    void getAlbums_artistNotFound() {
        Pageable pageable = mock(Pageable.class);
        Artist artist = ArtistInfoFixture.getArtistInfo();

        when(artistRepository.findById(artist.getId())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> artistService.getAlbums(any(), pageable));
        assertEquals(ErrorCode.ARTIST_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void getArtistShow_success() {

        Pageable pageable = mock(Pageable.class);;
        Artist artist = ArtistInfoFixture.getArtistInfo();


        when(showRepository.findShowsByArtistId(eq(artist.getId()), eq(pageable))).thenReturn(Page.empty());
        assertDoesNotThrow(() -> artistService.getArtistShow(artist.getId(), pageable));

    }
}
