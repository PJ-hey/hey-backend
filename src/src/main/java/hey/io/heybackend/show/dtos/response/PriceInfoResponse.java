package hey.io.heybackend.show.dtos.response;

import hey.io.heybackend.show.entities.PriceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PriceInfoResponse {

    private String type;
    private Integer price;

    public PriceInfoResponse(PriceInfo priceInfo) {
        this.type = priceInfo.getType();
        this.price = priceInfo.getPrice();
    }

}
