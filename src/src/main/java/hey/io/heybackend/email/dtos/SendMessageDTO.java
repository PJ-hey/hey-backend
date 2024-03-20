package hey.io.heybackend.email.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendMessageDTO {
    private String to;
    private String code;
}
