package hey.io.heybackend.show.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PriceInfo extends CommonModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;

    private String type;
    private Integer price;

    public static PriceInfo of(String type, Integer price) {
        return new PriceInfo(type, price);
    }

    public PriceInfo(String type, Integer price) {
        this.type = type;
        this.price = price;
    }

    public void setShow(Show show) {
        this.show = show;
    }


}
