package hey.io.heybackend.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
public class VerifyCodeBody {
    @Setter
    private String code;
}
