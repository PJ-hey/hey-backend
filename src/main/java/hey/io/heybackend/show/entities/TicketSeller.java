package hey.io.heybackend.show.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "ticket_seller")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties({"show", "createdAt", "updatedAt"})
public class TicketSeller extends CommonModel {

    private String name;
    private String baseUrl;
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;

    public static TicketSeller of(String name, String baseUrl, String icon) {
        return new TicketSeller(name, baseUrl, icon);
    }

    public TicketSeller(String name, String baseUrl, String icon) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.icon = icon;
    }

    public void setShow(Show show) {
        this.show = show;
    }

}