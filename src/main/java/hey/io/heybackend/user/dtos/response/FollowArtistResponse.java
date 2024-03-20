package hey.io.heybackend.user.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowArtistResponse {

    private Long userId;
    private Long artistId;
    private String message;
}

