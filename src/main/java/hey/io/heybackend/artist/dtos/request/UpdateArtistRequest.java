package hey.io.heybackend.artist.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArtistRequest {

    private String name;
    private String profileImage;
    private String genre;
    private LocalDateTime debutDate;

}
