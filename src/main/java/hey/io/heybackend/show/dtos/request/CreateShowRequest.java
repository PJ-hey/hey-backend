package hey.io.heybackend.show.dtos.request;

import hey.io.heybackend.show.entities.PriceInfo;
import hey.io.heybackend.show.entities.TicketSeller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShowRequest {

    private String name;

    @Builder.Default
    private List<PriceInfo> priceInfos = new ArrayList<>();

    @Builder.Default
    private List<TicketSeller> ticketSellers = new ArrayList<>();

    private String urlId;
    private LocalDateTime ticketOpenTime;
    private LocalDateTime date;
    private Integer strictedAge;
    private LocalTime runningTime;
    private String place;
    private String type;
    private String genre;
    private List<String> images;
    private Boolean isConfirmed;

}
