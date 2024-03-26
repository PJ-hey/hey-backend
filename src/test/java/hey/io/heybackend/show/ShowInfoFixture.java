package hey.io.heybackend.show;

import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.dtos.response.PriceInfoResponse;
import hey.io.heybackend.show.dtos.response.ShowArtistResponse;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.dtos.response.TicketSellerResponse;
import hey.io.heybackend.show.entities.PriceInfo;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.entities.ShowArtist;
import hey.io.heybackend.show.entities.TicketSeller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowInfoFixture {



    public static Show getShowInfo() {


        Show show = Show.builder()
                .name("Show")
                .priceInfos(Arrays.asList(PriceInfo.of("Regular", 10000), PriceInfo.of("VIP", 20000)))
                .ticketSellers(Arrays.asList(TicketSeller.of("yes24", "https://yes24.com", "yes24-icon.png")))
                .urlId("test-show")
                .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                .strictedAge(18)
                .runningTime(LocalTime.of(2, 30))
                .place("Theater")
                .type("Concert")
                .genre("Pop")
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();

        List<String> artistNames = Arrays.asList("carti");
        Artist artist = ArtistInfoFixture.getArtistInfo();
        ShowArtist showArtist = ShowArtist.of(show, artist);
        show.addShowArtist(showArtist);

        return show;
    }

    public static CreateShowRequest getCreateRequestInfo() {
        CreateShowRequest request = CreateShowRequest.builder()
                .name("Show")
                .priceInfos(Arrays.asList(PriceInfo.of("Regular", 10000), PriceInfo.of("VIP", 20000)))
                .ticketSellers(Arrays.asList(TicketSeller.of("yes24", "https://yes24.com", "yes24-icon.png")))
                .urlId("test-show")
                .artistNames(Arrays.asList("artist1"))
                .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                .strictedAge(18)
                .runningTime(LocalTime.of(2, 30))
                .place("Theater")
                .type("Concert")
                .genre("Pop")
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();

        return request;
    }

    public static UpdateShowRequest getUpdateRequestInfo() {
        UpdateShowRequest request = UpdateShowRequest.builder()
                .name("Show")
                .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                .strictedAge(18)
                .runningTime(LocalTime.of(2, 30))
                .place("Theater")
                .type("Concert")
                .genre("Pop")
                .isConfirmed(true)
                .build();

        return request;
    }



    public static ShowResponse getCreateShowResponseInfo(CreateShowRequest request) {

        Show show = ShowInfoFixture.getShowInfo();

        ShowResponse response = ShowResponse.builder()
                .id(1L)
                .name(request.getName())
                .priceInfos(request.getPriceInfos().stream()
                        .map(priceInfo -> new PriceInfoResponse(priceInfo))
                        .collect(Collectors.toList()))
                .ticketSellers(request.getTicketSellers().stream()
                        .map(ticketSeller -> new TicketSellerResponse(ticketSeller))
                        .collect(Collectors.toList()))
                .urlId("test-show")
                .artists(show.getArtists().stream()
                        .map(showArtist -> ShowArtistResponse.fromArtist(showArtist.getArtist()))
                        .collect(Collectors.toList()))
                .ticketOpenTime(request.getTicketOpenTime())
                .date(request.getDate())
                .strictedAge(request.getStrictedAge())
                .runningTime(request.getRunningTime())
                .place(request.getPlace())
                .type(request.getType())
                .genre(request.getGenre())
                .poster("image1.jpg")
                .detailImages(Arrays.asList("image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();
        return response;
    }

    public static ShowResponse getUpdateShowResponseInfo(UpdateShowRequest request) {
        ShowResponse response = ShowResponse.builder()
                .id(1L)
                .name(request.getName())
                .urlId("test-show")
                .ticketOpenTime(request.getTicketOpenTime())
                .date(request.getDate())
                .strictedAge(request.getStrictedAge())
                .runningTime(request.getRunningTime())
                .place(request.getPlace())
                .type(request.getType())
                .genre(request.getGenre())
                .poster("image1.jpg")
                .detailImages(Arrays.asList("image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();
        return response;
    }

    public static ShowArtist getShowArtistInfo(Show show, Artist artist) {
        ShowArtist showArtist = ShowArtist.builder()
                .show(show)
                .artist(artist)
                .build();

        return showArtist;
    }

    public static ShowArtistResponse getShowArtistResponseInfo() {
        ShowArtistResponse response = ShowArtistResponse.builder()
                .id(1L)
                .name("artist1")
                .profileImage("image1")
                .build();

        return response;
    }
}
