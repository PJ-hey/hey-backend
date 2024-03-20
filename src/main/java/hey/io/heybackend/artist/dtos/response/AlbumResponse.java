package hey.io.heybackend.artist.dtos.response;

import hey.io.heybackend.artist.entities.Album;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlbumResponse {

    private String coverImg;
    private String url;
    private String title;
    private LocalDateTime releaseDate;

    public AlbumResponse(Album album) {
        this.coverImg = album.getCoverImg();
        this.url = album.getUrl();
        this.title = album.getTitle();
        this.releaseDate = album.getReleaseDate();
    }
}
