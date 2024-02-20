package hey.io.heybackend.authToken.repository;

import hey.io.heybackend.authToken.entities.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
}
