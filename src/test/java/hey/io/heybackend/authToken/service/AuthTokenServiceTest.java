package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.dtos.VerifyCodeDTO;
import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import hey.io.heybackend.common.exceptions.CustomException;
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

import java.time.LocalDateTime;
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
    public void createToken_save_failed() {
        Mockito.when(authTokenRepository.save(Mockito.any())).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(CustomException.class, () -> authTokenService.createToken("123@naver.com"));
    }

    @Test
    public void createToken_save_success() {
        AuthToken mockToken = new AuthToken();
        Mockito.when(authTokenRepository.save(Mockito.any())).thenReturn(mockToken);
        Assertions.assertEquals(mockToken, authTokenService.createToken("123@naver.com"));
    }

    @Test
    public void createToken_attemptedCount_beforeToday() {
        AuthToken mockToken = new AuthToken();
        mockToken.setUpdatedAt(LocalDateTime.now().minusDays(1));
        mockToken.setAttemptedCount(5);
        authTokenService.createToken("");
    }

    @Test
    public void createToken_attemptedCount_Today() {
        AuthToken mockToken = new AuthToken();
        mockToken.setUpdatedAt(LocalDateTime.now());
        mockToken.setAttemptedCount(5);
        Mockito.when(authTokenRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(mockToken));
        authTokenService.createToken("");
    }

    @Test
    public void findTokenByEmail_failed() {
        Mockito.when(authTokenRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomException.class, () -> authTokenService.findTokenByEmail("123@naver.com"));
    }

    @Test
    public void findTokenByEmail_success() {
        AuthToken token = new AuthToken();
        Mockito.when(authTokenRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(token));
        Assertions.assertEquals(token, authTokenService.findTokenByEmail("123@naver.com"));
    }

    @Test
    public void findTokenByUUID_failed() {
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomException.class, () -> authTokenService.findTokenByUUID(UUID.randomUUID()));
    }

    @Test
    public void findTokenByUUID_success() {
        AuthToken token = new AuthToken();
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(token));
        Assertions.assertEquals(token, authTokenService.findTokenByUUID(UUID.randomUUID()));
    }

    @Test
    public void verifyCode_Expired_failed() {
        AuthToken token = new AuthToken();
        token.setExpiredAt(OffsetDateTime.now().minusMinutes(100));
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(token));
        VerifyCodeDTO dto = new VerifyCodeDTO(UUID.randomUUID(), "");
        Assertions.assertThrows(CustomException.class, () -> authTokenService.verifyCode(dto));
    }

    @Test
    public void verifyCode_AttemptedCount_failed() {
        AuthToken token = new AuthToken();
        token.setAttemptedCount(6);
        token.setExpiredAt(OffsetDateTime.now().plusMinutes(10));
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(token));
        VerifyCodeDTO dto = new VerifyCodeDTO(UUID.randomUUID(), "");
        Assertions.assertThrows(CustomException.class, () -> authTokenService.verifyCode(dto));
    }

    @Test
    public void verifyCode_success() {
        AuthToken token = new AuthToken();
        token.setAttemptedCount(0);
        token.setExpiredAt(OffsetDateTime.now().plusMinutes(10));
        token.setVerificationCode("");
        Mockito.when(authTokenRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(token));
        VerifyCodeDTO dto = new VerifyCodeDTO(UUID.randomUUID(), "");
        Assertions.assertEquals(token, authTokenService.verifyCode(dto));
    }
}
