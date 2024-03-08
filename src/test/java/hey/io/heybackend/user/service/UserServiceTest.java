package hey.io.heybackend.user.service;

import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.dtos.response.UserResponse;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, new BCryptPasswordEncoder());
    }

    @Test
    public void createUser_success() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");

        User user = new User(request);
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.createUser(request));

    }

    @Test
    public void createUser_save_failed() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");

        User user = new User(request);
        when(userRepository.save(any())).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThrows(CustomException.class, () -> userService.createUser(request));
    }

    @Test
    public void createUser_validation_failed() {
        CreateUserRequest request = new CreateUserRequest("", "12345678", "test", "1111111111", "test");

        assertThrows(CustomException.class, () -> userService.createUser(request));
    }

    @Test
    public void getUser_success() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UserResponse response = userService.getUser(any());

        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getUserName()).isEqualTo(user.getUserName());
        assertThat(response.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(response.getNickName()).isEqualTo(user.getNickName());
    }

    @Test
    public void getUser_userNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> userService.getUser(any()));
    }

    @Test
    public void updateUser_success() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest("test2", "1111111111");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UpdateUserResponse response = userService.updateUser(any(), updateUserRequest);

        assertThat(response.getNickName()).isEqualTo(user.getNickName());
    }

    @Test
    public void updateUser_userNotFound() {
        UpdateUserRequest request = new UpdateUserRequest("test2", "1111111111");
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> userService.updateUser(any(), request));
    }
    @Test
    public void updateUser_duplicatedNickName() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest("test", "1111111111");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.existsUserByNickName(request.getNickName())).thenReturn(true);

        assertThrows(CustomException.class, () -> userService.updateUser(any(), updateUserRequest));
    }

    @Test
    public void deleteUser_success() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");
        User user = new User(request);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.deleteUser(any());

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUser_userNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> userService.deleteUser(any()));
        verify(userRepository, never()).delete(any());
    }
}

