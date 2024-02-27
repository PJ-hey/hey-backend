package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.dtos.VerifyCodeDTO;
import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import org.springframework.stereotype.Service;

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
            if (newAuthToken.getAttemptedCount() > 5) {
                throw new CustomException(ErrorCode.TOKEN_ATTEMPTED_COUNT_EXCEED);
            }
            if (newAuthToken.getUpdatedAt().toLocalDate().isBefore(LocalDate.now())) {
                newAuthToken.setAttemptedCount(0);
            }

            attemptedCount = newAuthToken.getAttemptedCount();
        } catch (CustomException e) {
            if (!e.getErrorCode().equals(ErrorCode.TOKEN_ATTEMPTED_COUNT_EXCEED)) {
                newAuthToken = new AuthToken();
            } else {
                throw e;
            }
        }


        OffsetDateTime expireDate = OffsetDateTime.now().plusMinutes(10);
        newAuthToken.setEmail(email);
        newAuthToken.setUuid(this.generateRandomUUID());
        newAuthToken.setExpiredAt(expireDate);
        newAuthToken.setVerificationCode(this.generateRandomCode());
        newAuthToken.setAttemptedCount(attemptedCount + 1);
        try {
            return authTokenRepository.save(newAuthToken);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.TOKEN_SAVED_FAILED);
            // throw Internal Server Error
            //throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public AuthToken findTokenByEmail(String email) {
        Optional<AuthToken> token = authTokenRepository.findByEmail(email);
        // throw TokenNotFound error
        return token.orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));
    }

    public AuthToken findTokenByUUID(UUID uuid) {
        Optional<AuthToken> token = authTokenRepository.findByUuid(uuid);
        return token.orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));
    }

    public AuthToken verifyCode(VerifyCodeDTO dto) {
        AuthToken token = this.findTokenByUUID(dto.getUuid());

        if (token.getExpiredAt().isBefore(OffsetDateTime.now())) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        if (token.getAttemptedCount() > 5) {
            throw new CustomException(ErrorCode.TOKEN_ATTEMPTED_COUNT_EXCEED);
        }
        if (!token.getVerificationCode().equals(dto.getCode())) {
            throw new CustomException(ErrorCode.TOKEN_VERIFY_FAILED);
        }

        return token;
    }

    public UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public String generateRandomCode() {
        return String.format("%06d", rand.nextInt(100001));
    }
}
