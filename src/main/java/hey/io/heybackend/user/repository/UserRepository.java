package hey.io.heybackend.user.repository;

import hey.io.heybackend.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);


    boolean existsUserByNickName(String nickName);
}
