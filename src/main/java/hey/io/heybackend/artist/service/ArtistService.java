package hey.io.heybackend.artist.service;

import hey.io.heybackend.artist.dtos.request.CreateArtistRequest;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.artist.dtos.response.AlbumResponse;
import hey.io.heybackend.artist.dtos.response.ArtistResponse;
import hey.io.heybackend.artist.entities.Album;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.AlbumRepository;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public ArtistResponse createArtist(CreateArtistRequest request) {

        Artist artist = Artist.builder()
                .name(request.getName())
                .profileImage(request.getProfileImage())
                .genre(request.getGenre())
                .debutDate(request.getDebutDate())
                .albums(new ArrayList<>())
                .build();

        List<Album> albums = request.getAlbums().stream()
                .map(album -> Album.of(album.getCoverImg(), album.getUrl(), album.getTitle(), album.getReleaseDate()))
                .collect(Collectors.toList());

        artist.addAlbum(albums);

        Artist savedArtist = artistRepository.save(artist);

        return new ArtistResponse(savedArtist);
    }

    public ArtistResponse getArtistInfo(Long artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        return new ArtistResponse(artist);
    }

    @Transactional
    public ArtistResponse updateArtist(Long artistId, UpdateArtistRequest request) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        artist.updateArtist(request);

        return new ArtistResponse(artist);
    }

    @Transactional
    public void deleteArtist(Long artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        artistRepository.delete(artist);

    }

    public Page<AlbumResponse> getAlbums(Long artistId, Pageable pageable) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        Page<Album> albumPage = albumRepository.findByArtist(artist, pageable);

        return albumPage.map(AlbumResponse::new);
    }

}
