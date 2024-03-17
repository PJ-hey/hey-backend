package hey.io.heybackend.artist.service;

import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.dtos.request.CreateArtistRequest;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArtistServiceTest {

    @Autowired
    private ArtistService artistService;

    @MockBean
    private ArtistRepository artistRepository;

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
}
