package hey.io.heybackend.artist.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "\"album\"")
@NoArgsConstructor
@AllArgsConstructor
public class Album extends CommonModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private String coverImg;
    private String url;
    private String title;
    private LocalDateTime releaseDate;

    public static Album of(String coverImg, String url, String title, LocalDateTime releaseDate) {
        return new Album(coverImg, url, title, releaseDate);
    }

    public Album(String coverImg, String url, String title, LocalDateTime releaseDate) {
        this.coverImg = coverImg;
        this.url = url;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}
