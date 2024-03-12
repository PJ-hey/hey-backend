package hey.io.heybackend.show.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hey.io.heybackend.common.entities.CommonModel;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.user.entities.UserFollowShow;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "\"show\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Show extends CommonModel {


    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PriceInfo> priceInfos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TicketSeller> ticketSellers = new ArrayList<>();

    private String urlId;

//    private Artist artist;

    private LocalDateTime ticketOpenTime;
    private LocalDateTime date;
    private Integer strictedAge;
    private LocalTime runningTime;
    private String place;
    private String type;
    private String genre;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> images = new ArrayList<>();

    private Boolean isConfirmed;


    public void updateShow(UpdateShowRequest request) {
        this.name = request.getName();
        this.ticketOpenTime = request.getTicketOpenTime();
        this.date = request.getDate();
        this.strictedAge = request.getStrictedAge();
        this.runningTime = request.getRunningTime();
        this.place = request.getPlace();
        this.type = request.getType();
        this.genre = request.getGenre();
        this.isConfirmed = request.getIsConfirmed();
    }

    public void addPriceInfo(List<PriceInfo> priceInfos) {

        this.priceInfos.addAll(priceInfos);
        priceInfos.forEach(priceInfo -> priceInfo.setShow(this));

    }

    public void addTicketSeller(List<TicketSeller> ticketSellers) {

        this.ticketSellers.addAll(ticketSellers);
        ticketSellers.forEach(ticketSeller -> ticketSeller.setShow(this));

    }


}
