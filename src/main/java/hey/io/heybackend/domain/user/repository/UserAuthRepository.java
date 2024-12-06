package hey.io.heybackend.domain.user.repository;

import hey.io.heybackend.domain.user.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    List<UserAuth> findByUserId(String userId);
}
