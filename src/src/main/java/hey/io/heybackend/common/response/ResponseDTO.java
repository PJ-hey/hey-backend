package hey.io.heybackend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO<T> {
    private Boolean ok;
    private Optional<T> data;
}
