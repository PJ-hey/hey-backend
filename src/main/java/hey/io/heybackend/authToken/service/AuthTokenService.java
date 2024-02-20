package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.time.OffsetDateTime;
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
        AuthToken newAuthToken = new AuthToken();

        OffsetDateTime expireDate = OffsetDateTime.now().plusMinutes(10);

        newAuthToken.setUuid(this.generateRandomUUID());
        newAuthToken.setExpiredAt(expireDate);
        newAuthToken.setVerificationCode(this.generateRandomCode());
        try {
            AuthToken generatedToken = authTokenRepository.save(newAuthToken);

            return generatedToken;
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void findTokenByEmail() {

    }

    public void findTokenByUUID() {

    }

    private UUID generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    private String generateRandomCode() {
        String VerificationCode = String.format("%04d", rand.nextInt(1001));

        return VerificationCode;
    }
}
