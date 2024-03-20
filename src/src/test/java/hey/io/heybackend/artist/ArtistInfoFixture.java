package hey.io.heybackend.artist;

import hey.io.heybackend.artist.dtos.request.CreateArtistRequest;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.artist.dtos.response.ArtistResponse;
import hey.io.heybackend.artist.entities.Album;
import hey.io.heybackend.artist.entities.Artist;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ArtistInfoFixture {

    public static Artist getArtistInfo() {

        Artist artist = Artist.builder()
                .name("carti")
                .profileImage("image1.jpg")
                .genre("rock")
                .debutDate(LocalDateTime.of(2024, 4, 1, 19, 0))
                .albums(Arrays.asList(Album.of("image2.jpg", "carti.com", "carti", LocalDateTime.of(2024, 4, 1, 19, 0)), Album.of("image3.jpg", "carti2.com", "carti2", LocalDateTime.of(2024, 4, 1, 19, 0))))
                .build();

        return artist;
    }

    public static CreateArtistRequest getCreateRequestInfo() {

        CreateArtistRequest request = CreateArtistRequest.builder()
                .name("carti")
                .profileImage("image1.jpg")
                .genre("rock")
                .debutDate(LocalDateTime.of(2024, 4, 1, 19, 0))
                .albums(Arrays.asList(Album.of("image2.jpg", "carti.com", "carti", LocalDateTime.of(2024, 4, 1, 19, 0)), Album.of("image3.jpg", "carti2.com", "carti2", LocalDateTime.of(2024, 4, 1, 19, 0))))
                .build();

        return request;
    }

    public static UpdateArtistRequest getUpdateRequestInfo() {
        UpdateArtistRequest request = UpdateArtistRequest.builder()
                .name("carti3")
                .profileImage("image3.jpg")
                .genre("rock")
                .debutDate(LocalDateTime.of(2024, 4, 1, 19, 0))
                .build();

        return request;
    }

    public static ArtistResponse getCreateArtistResponseInfo(CreateArtistRequest request) {
        ArtistResponse response = ArtistResponse.builder()
                .id(1l)
                .name(request.getName())
                .profileImage(request.getProfileImage())
                .genre(request.getGenre())
                .debutDate(request.getDebutDate())
                .albums(Arrays.asList(Album.of("image2.jpg", "carti.com", "carti", LocalDateTime.of(2024, 4, 1, 19, 0)), Album.of("image3.jpg", "carti2.com", "carti2", LocalDateTime.of(2024, 4, 1, 19, 0))))
                .build();

        return response;
    }

    public static ArtistResponse getUpdateArtistResponseInfo(UpdateArtistRequest request) {
        ArtistResponse response = ArtistResponse.builder()
                .id(1L)
                .name(request.getName())
                .profileImage(request.getProfileImage())
                .genre(request.getGenre())
                .debutDate(request.getDebutDate())
                .build();

        return response;
    }

}
