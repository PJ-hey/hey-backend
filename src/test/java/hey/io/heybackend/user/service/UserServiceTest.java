package hey.io.heybackend.user.service;

import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.HttpServerErrorException;

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
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Assertions.assertEquals(user, userService.createUser(request));
    }

    @Test
    public void createUser_save_failed() {
        CreateUserRequest request = new CreateUserRequest("123@naver.com", "12345678", "test", "1111111111", "test");

        User user = new User(request);
        Mockito.when(userRepository.save(Mockito.any())).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(CustomException.class, () -> userService.createUser(request));
    }

    @Test
    public void createUser_validation_failed() {
        CreateUserRequest request = new CreateUserRequest("", "12345678", "test", "1111111111", "test");

        Assertions.assertThrows(CustomException.class, () -> userService.createUser(request));
    }
}
