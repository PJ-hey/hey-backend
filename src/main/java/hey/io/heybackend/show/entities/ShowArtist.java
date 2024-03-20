package hey.io.heybackend.show.entities;

import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class ShowArtist extends CommonModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public static ShowArtist of(Show show, Artist artist) {
        return new ShowArtist(show, artist);
    }

    public ShowArtist(Show show, Artist artist) {
        this.show = show;
        this.artist = artist;
    }

}
