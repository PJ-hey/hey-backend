package hey.io.heybackend.user.service;

import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.show.ShowInfoFixture;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.repository.ShowRepository;
import hey.io.heybackend.user.UserInfoFixture;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import hey.io.heybackend.user.dtos.request.FollowShowListRequest;
import hey.io.heybackend.user.dtos.request.FollowShowRequest;
import hey.io.heybackend.user.dtos.response.FollowShowResponse;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.entities.UserFollowShow;
import hey.io.heybackend.user.repository.UserFollowShowRepository;
import hey.io.heybackend.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserFollowServiceTest {

    @Autowired
    private UserFollowService userFollowService;

    @MockBean
    private UserFollowShowRepository userFollowShowRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ShowRepository showRepository;

    @Test
    void followShow_success() {
        CreateUserRequest createUserRequest = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(createUserRequest);
        Show show = ShowInfoFixture.getShowInfo();
        FollowShowRequest followShowRequest = UserInfoFixture.getFollowShowRequestInfo();


        when(userRepository.findById(followShowRequest.getUserId())).thenReturn(Optional.of(user));
        when(showRepository.findById(followShowRequest.getShowId())).thenReturn(Optional.of(show));
        when(userFollowShowRepository.save(any())).thenReturn(mock(UserFollowShow.class));

        Assertions.assertDoesNotThrow(() -> userFollowService.followShow(followShowRequest));

    }

    @Test
    void followShow_userNotFound() {
        FollowShowRequest request = UserInfoFixture.getFollowShowRequestInfo();
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());
        when(userFollowShowRepository.save(any())).thenReturn(mock(UserFollowShow.class));
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userFollowService.followShow(request));

        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void followShow_showNotFound() {

        CreateUserRequest createUserRequest = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(createUserRequest);
        FollowShowRequest request = UserInfoFixture.getFollowShowRequestInfo();

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(showRepository.findById(request.getShowId())).thenReturn(Optional.empty());
        when(userFollowShowRepository.save(any())).thenReturn(mock(UserFollowShow.class));

        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userFollowService.followShow(request));

        Assertions.assertEquals(ErrorCode.SHOW_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void getFollowShow_success() {
        CreateUserRequest createUserRequest = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(createUserRequest);

        FollowShowListRequest request = UserInfoFixture.getFollowShowListRequestInfo();

        Pageable pageable = mock(Pageable.class);

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(userFollowShowRepository.findUserFollowShowByUserId(user.getId(), pageable)).thenReturn(Page.empty());

        assertDoesNotThrow(() -> userFollowService.getFollowShow(request, pageable));
    }


    @Test
    void getFollowShow_userNotFound() {
        FollowShowListRequest request = UserInfoFixture.getFollowShowListRequestInfo();

        Pageable pageable = mock(Pageable.class);

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());

        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userFollowService.getFollowShow(request, pageable));

        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());

    }
}
