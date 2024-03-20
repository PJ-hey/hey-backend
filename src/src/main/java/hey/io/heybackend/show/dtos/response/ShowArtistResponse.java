package hey.io.heybackend.show.dtos.response;

import hey.io.heybackend.artist.entities.Artist;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShowArtistResponse {

    private Long id;
    private String name;
    private String profileImage;

    public static ShowArtistResponse fromArtist(Artist artist) {
        return ShowArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .profileImage(artist.getProfileImage())
                .build();
    }
}
