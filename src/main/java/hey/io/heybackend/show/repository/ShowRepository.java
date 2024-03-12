package hey.io.heybackend.show.repository;

import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.user.dtos.request.FollowShowListRequest;
import hey.io.heybackend.user.entities.UserFollowShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    Page<Show> findByIsConfirmedTrue(Pageable pageable);



}