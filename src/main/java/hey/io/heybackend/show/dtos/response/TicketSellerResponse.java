package hey.io.heybackend.show.dtos.response;

import hey.io.heybackend.show.entities.TicketSeller;
import lombok.Getter;

@Getter
public class TicketSellerResponse {

    private String name;
    private String baseUrl;
    private String icon;

    public TicketSellerResponse(TicketSeller ticketSeller) {
        this.name = ticketSeller.getName();
        this.baseUrl = ticketSeller.getBaseUrl();
        this.icon = ticketSeller.getIcon();
    }

}
