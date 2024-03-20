package hey.io.heybackend.artist.dtos.request;

import hey.io.heybackend.artist.entities.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateArtistRequest {

    private String name;
    private String profileImage;
    private String genre;
    private LocalDateTime debutDate;

    @Builder.Default
    private List<Album> albums = new ArrayList<>();
}
