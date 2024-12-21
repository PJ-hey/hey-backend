package hey.io.heybackend.domain.member.repository;

import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.entity.MemberInterest;
import hey.io.heybackend.domain.member.enums.InterestCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {

    List<InterestCode> findByMember_MemberId(Long memberId);

    void deleteByMember(Member member);
}
