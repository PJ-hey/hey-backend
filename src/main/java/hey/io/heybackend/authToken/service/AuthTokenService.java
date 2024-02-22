package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;
    private final Random rand;

    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.rand = new Random();
        this.authTokenRepository = authTokenRepository;
    }

    public AuthToken createToken(String email) {
        AuthToken newAuthToken;
        int attemptedCount = 0;
        try {
            newAuthToken = this.findTokenByEmail(email);
            if (newAuthToken.getUpdatedAt().toLocalDate().isBefore(LocalDate.now())) {
                newAuthToken.setAttemptedCount(0);
            }
            attemptedCount = newAuthToken.getAttemptedCount();
        } catch (HttpClientErrorException e) {
            newAuthToken = new AuthToken();
        }

        OffsetDateTime expireDate = OffsetDateTime.now().plusMinutes(10);
        newAuthToken.setEmail(email);
        newAuthToken.setUuid(this.generateRandomUUID());
        newAuthToken.setExpiredAt(expireDate);
        newAuthToken.setVerificationCode(this.generateRandomCode());
        newAuthToken.setAttemptedCount(attemptedCount + 1);
        System.out.println(newAuthToken.getAttemptedCount());
        try {
            return authTokenRepository.save(newAuthToken);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public AuthToken findTokenByEmail(String email) {
        Optional<AuthToken> token = authTokenRepository.findByEmail(email);
        return token.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public AuthToken findTokenByUUID(UUID uuid) {
        Optional<AuthToken> token = authTokenRepository.findByUuid(uuid);
        return token.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public boolean verifyCode(UUID uuid, String code) {
        AuthToken token = this.findTokenByUUID(uuid);

        if (token.getExpiredAt().isBefore(OffsetDateTime.now())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        if (token.getAttemptedCount() > 5) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return token.getVerificationCode().equals(code);
    }

    public UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public String generateRandomCode() {
        return String.format("%04d", rand.nextInt(1001));
    }
}
