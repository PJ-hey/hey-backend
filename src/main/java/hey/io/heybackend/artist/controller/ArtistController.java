package hey.io.heybackend.artist.controller;

import hey.io.heybackend.artist.dtos.request.CreateArtistRequest;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.artist.dtos.response.AlbumResponse;
import hey.io.heybackend.artist.dtos.response.ArtistResponse;
import hey.io.heybackend.artist.dtos.response.ArtistShowResponse;
import hey.io.heybackend.artist.service.ArtistService;
import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.show.dtos.response.ShowListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ArtistResponse>> createArtist(@RequestBody CreateArtistRequest request) {

        ArtistResponse artistResponse = artistService.createArtist(request);

        ResponseDTO<ArtistResponse> responseDTO = new ResponseDTO<>(true, Optional.of(artistResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ArtistResponse>> getArtistInfo(@PathVariable("id") Long artistId) {

        ArtistResponse artistResponse = artistService.getArtistInfo(artistId);
        ResponseDTO<ArtistResponse> responseDTO = new ResponseDTO<>(true, Optional.of(artistResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ArtistResponse>> updateArtist(@PathVariable("id") Long artistId, @RequestBody UpdateArtistRequest request) {

        ArtistResponse artistResponse = artistService.updateArtist(artistId, request);
        ResponseDTO<ArtistResponse> responseDTO = new ResponseDTO<>(true, Optional.of(artistResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteArtist(@PathVariable("id") Long artistId) {

        artistService.deleteArtist(artistId);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}/album")
    public ResponseEntity<ResponseDTO<Page<AlbumResponse>>> getAlbum(@PathVariable("id") Long artistId, Pageable pageable) {

        Page<AlbumResponse> albumResponse = artistService.getAlbums(artistId, pageable);
        ResponseDTO<Page<AlbumResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(albumResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}/show")
    public ResponseEntity<ResponseDTO<Page<ShowListResponse>>> getArtistShow(@PathVariable("id") Long artistId, Pageable pageable) {

        Page<ShowListResponse> showListResponses = artistService.getArtistShow(artistId, pageable);
        ResponseDTO<Page<ShowListResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(showListResponses));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
