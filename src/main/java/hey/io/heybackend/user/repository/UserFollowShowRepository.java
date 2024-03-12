package hey.io.heybackend.user.repository;

import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.entities.UserFollowShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFollowShowRepository extends JpaRepository<UserFollowShow, Long> {

    Optional<UserFollowShow> findUserFollowByUserAndShow(User user, Show show);

    Page<UserFollowShow> findUserFollowShowByUserId(Long userId, Pageable pageable);

}
