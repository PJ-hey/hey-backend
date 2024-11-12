package hey.io.heybackend.domain.system.repository;

import hey.io.heybackend.domain.system.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByRefreshToken(String refreshToken);

    Token findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);

}