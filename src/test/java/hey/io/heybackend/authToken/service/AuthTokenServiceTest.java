package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthTokenServiceTest {

    private AuthTokenService authTokenService;

    @Mock
    private AuthTokenRepository authTokenRepository;

    @BeforeEach
    public void setup() {
        authTokenService = new AuthTokenService(authTokenRepository);
    }

    @Test
    public void CreateAuthToken_failed() {
        Mockito.when(authTokenRepository.save(Mockito.any())).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(HttpServerErrorException.class, () -> authTokenService.createToken("123@naver.com"));
    }

    @Test
    public void CreateAuthToken_Success() {

        Mockito.when(authTokenRepository.save(Mockito.any())).thenReturn();
    }
}
