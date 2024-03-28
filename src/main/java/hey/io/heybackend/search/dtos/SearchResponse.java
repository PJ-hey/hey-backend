package hey.io.heybackend.search.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.show.entities.Show;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse {

    private Long showId;
    private String showName;
    private String urlId;
    private LocalDateTime ticketOpenTime;
    private LocalDateTime date;
    private String poster;
    private String place;

    private Long artistId;
    private String artistName;
    private String profileImage;

    public static SearchResponse fromShow(Show show) {
        return SearchResponse.builder()
                .showId(show.getId())
                .showName(show.getName())
                .urlId(show.getUrlId())
                .ticketOpenTime(show.getTicketOpenTime())
                .date(show.getDate())
                .poster(show.getImages().get(0))
                .place(show.getPlace())
                .build();
    }

    public static SearchResponse fromArtist(Artist artist) {
        return SearchResponse.builder()
                .artistId(artist.getId())
                .artistName(artist.getName())
                .profileImage(artist.getProfileImage())
                .build();
    }
}
