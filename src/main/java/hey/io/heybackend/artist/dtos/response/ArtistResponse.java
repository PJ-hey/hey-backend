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

    private List<Album> albums = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ArtistResponse(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.profileImage = artist.getProfileImage();
        this.genre = artist.getGenre();
        this.debutDate = artist.getDebutDate();
        this.albums = artist.getAlbums();
        this.createdAt = artist.getCreatedAt();
        this.updatedAt = artist.getUpdatedAt();

    }


}
