package hey.io.heybackend.user.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowShowResponse {

    private Long userId;
    private Long showId;
    private String message;

}
