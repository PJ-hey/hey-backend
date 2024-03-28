package hey.io.heybackend.artist.dtos.response;

import hey.io.heybackend.artist.entities.Album;
import hey.io.heybackend.artist.entities.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponse {

    private Long id;
    private String name;
    private String profileImage;
    private String genre;
    private LocalDateTime debutDate;

    private List<AlbumResponse> albums = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ArtistResponse(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.profileImage = artist.getProfileImage();
        this.genre = artist.getGenre();
        this.debutDate = artist.getDebutDate();
        this.albums = artist.getAlbums().stream()
                .map(album -> new AlbumResponse(album))
                .collect(Collectors.toList());
        this.createdAt = artist.getCreatedAt();
        this.updatedAt = artist.getUpdatedAt();
        this.albums = artist.getAlbums();
        this.createdAt = artist.getCreatedAt();
        this.updatedAt = artist.getUpdatedAt();

    }


}
