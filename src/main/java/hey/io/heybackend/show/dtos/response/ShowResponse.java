package hey.io.heybackend.show.dtos.response;

import hey.io.heybackend.show.entities.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {

    private Long id;
    private String name;
    private List<PriceInfoResponse> priceInfos = new ArrayList<>();
    private List<TicketSellerResponse> ticketSellers = new ArrayList<>();
    private String urlId;
    private List<ShowArtistResponse> artists = new ArrayList<>();
    private LocalDateTime ticketOpenTime;
    private LocalDateTime date;
    private Integer strictedAge;
    private LocalTime runningTime;
    private String place;
    private String type;
    private String genre;
    private String poster;
    private List<String> detailImages = new ArrayList<>();
    private Boolean isConfirmed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ShowResponse(Show show) {
        this.id = show.getId();
        this.name = show.getName();
        this.priceInfos = show.getPriceInfos().stream()
                .map(priceInfo -> new PriceInfoResponse(priceInfo))
                .collect(Collectors.toList());
        this.ticketSellers = show.getTicketSellers().stream()
                .map(ticketSeller -> new TicketSellerResponse(ticketSeller))
                .collect(Collectors.toList());
        this.urlId = show.getUrlId();
        this.artists = show.getArtists().stream()
                .map(showArtist -> ShowArtistResponse.fromArtist(showArtist.getArtist()))
                .collect(Collectors.toList());
        this.ticketOpenTime = show.getTicketOpenTime();
        this.date = show.getDate();
        this.strictedAge = show.getStrictedAge();
        this.runningTime = show.getRunningTime();
        this.place = show.getPlace();
        this.type = show.getType();
        this.genre = show.getGenre();

        if (!show.getImages().isEmpty()) {
            this.poster = show.getImages().get(0);
            this.detailImages.addAll(show.getImages().subList(1, show.getImages().size()));
        }

        this.isConfirmed = show.getIsConfirmed();
        this.createdAt = show.getCreatedAt();
        this.updatedAt = show.getUpdatedAt();
    }

}
