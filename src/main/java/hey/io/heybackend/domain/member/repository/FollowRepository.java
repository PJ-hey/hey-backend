package hey.io.heybackend.domain.member.repository;

import hey.io.heybackend.domain.member.entity.Follow;
import hey.io.heybackend.domain.member.enums.FollowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowTypeAndFollowTargetIdAndMember_MemberId(FollowType followType, Long targetId, Long memberId);

}
