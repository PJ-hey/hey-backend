package hey.io.heybackend.user.entities;

import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
public class UserFollowArtist extends CommonModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public static UserFollowArtist of(User user, Artist artist) {

        return new UserFollowArtist(user, artist);

    }

    public UserFollowArtist(User user, Artist artist) {
        this.user = user;
        this.artist = artist;
    }

}
