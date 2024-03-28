package hey.io.heybackend.show.dtos.response;

import hey.io.heybackend.show.entities.Show;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShowListResponse {

    private Long id;
    private String name;
    private String urlId;
    private LocalDateTime ticketOpenTime;
    private LocalDateTime date;
    private String poster;
    private String place;

    public ShowListResponse(Show show) {

        this.id = show.getId();
        this.name = show.getName();
        this.urlId = show.getUrlId();
        this.ticketOpenTime = show.getTicketOpenTime();
        this.date = show.getDate();
        this.poster = show.getImages().get(0);
        this.place = show.getPlace();

    }

}
