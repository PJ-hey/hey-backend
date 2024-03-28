package hey.io.heybackend.user.repository;

import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.entities.UserFollowArtist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFollowArtistRepository extends JpaRepository<UserFollowArtist, Long> {

    Optional<UserFollowArtist> findUserFollowByUserAndArtist(User user, Artist artist);

    Page<UserFollowArtist> findUserFollowArtistByUserId(Long userId, Pageable pageable);

}
