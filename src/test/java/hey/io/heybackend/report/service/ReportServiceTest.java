package hey.io.heybackend.report.service;

import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.report.ReportInfoFixture;
import hey.io.heybackend.report.dtos.request.ReportRequest;
import hey.io.heybackend.report.repository.ArtistReportRepository;
import hey.io.heybackend.report.repository.ShowReportRepository;
import hey.io.heybackend.show.ShowInfoFixture;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.repository.ShowRepository;
import hey.io.heybackend.user.UserInfoFixture;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static hey.io.heybackend.user.entities.QUser.user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ShowRepository showRepository;

    @MockBean
    private ArtistRepository artistRepository;

    @MockBean
    private ShowReportRepository showReportRepository;

    @MockBean
    private ArtistReportRepository artistReportRepository;


    @Test
    void reportShow_userNotFound() {

        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        Show show = ShowInfoFixture.getShowInfo();
        ReportRequest reportRequest = ReportInfoFixture.getReportRequestInfo();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(showRepository.findById(anyLong())).thenReturn(Optional.of(show));
        when(showReportRepository.save(any())).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> reportService.reportShow(any(), reportRequest));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void reportShow_showNotFound() {

        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        Show show = ShowInfoFixture.getShowInfo();
        ReportRequest reportRequest = ReportInfoFixture.getReportRequestInfo();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(showRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(showReportRepository.save(any())).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> reportService.reportShow(any(), reportRequest));
        assertEquals(ErrorCode.SHOW_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void reportArtist_userNotFound() {

        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        Artist artist = ArtistInfoFixture.getArtistInfo();
        ReportRequest reportRequest = ReportInfoFixture.getReportRequestInfo();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(artistRepository.findById(anyLong())).thenReturn(Optional.of(artist));
        when(showReportRepository.save(any())).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> reportService.reportArtist(any(), reportRequest));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void reportArtist_artistNotFound() {

        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        Artist artist = ArtistInfoFixture.getArtistInfo();
        ReportRequest reportRequest = ReportInfoFixture.getReportRequestInfo();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(showRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(showReportRepository.save(any())).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> reportService.reportArtist(any(), reportRequest));
        assertEquals(ErrorCode.ARTIST_NOT_FOUND, exception.getErrorCode());

    }

}
