package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.entities.AuthToken;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

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
        AuthToken mockToken = new AuthToken();
        Mockito.when(authTokenRepository.save(Mockito.any())).thenReturn(mockToken);
        Assertions.assertEquals(mockToken,authTokenService.createToken("123@naver.com"));
    }
    @Test
    public void findTokenByEmail_failed(){
        Mockito.when(authTokenRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(HttpClientErrorException.class, ()-> authTokenService.findTokenByEmail("123@naver.com"));
    }

    @Test
    public void findTokenByEmail_success(){
        AuthToken token = new AuthToken();
        Mockito.when(authTokenRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(token));
        Assertions.assertEquals(token,authTokenService.findTokenByEmail("123@naver.com"));
    }

    @Test
    public void findTokenByUUID_failed(){
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(HttpClientErrorException.class, ()-> authTokenService.findTokenByUUID(UUID.randomUUID()));
    }

    @Test
    public void findTokenByUUID_success(){
        AuthToken token = new AuthToken();
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(token));
        Assertions.assertEquals(token,authTokenService.findTokenByUUID(UUID.randomUUID()));
    }
}
