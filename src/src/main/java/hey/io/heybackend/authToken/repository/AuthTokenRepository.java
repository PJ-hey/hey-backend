package hey.io.heybackend.authToken.repository;

import hey.io.heybackend.authToken.entities.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByEmail(String email);
    Optional<AuthToken> findByUuid(UUID uuid);
}
