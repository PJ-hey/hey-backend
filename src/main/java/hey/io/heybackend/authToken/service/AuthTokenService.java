package hey.io.heybackend.authToken.service;

import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public void createToken() {

    }

    public void findTokenByEmail() {

    }

    public void findTokenByUUID() {

    }
}
