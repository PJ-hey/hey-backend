package hey.io.heybackend.domain.member.service;

import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.entity.MemberInterest;
import hey.io.heybackend.domain.member.enums.InterestCategory;
import hey.io.heybackend.domain.member.enums.InterestCode;
import hey.io.heybackend.domain.member.repository.MemberInterestRepository;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberInterestService {

    private final MemberRepository memberRepository;
    private final MemberInterestRepository memberInterestRepository;

    /**
     * <p>관심 분야 등록</p>
     *
     * @param member    회원 정보
     * @param typeList  관심 유형 목록
     * @param genreList 관심 장르 목록
     */
    @Transactional
    public void insertMemberInterest(Member member, List<InterestCode> typeList,
        List<InterestCode> genreList) {
        // 관심 유형 저장
        if (typeList != null) {
            typeList.forEach(type -> {
                MemberInterest memberInterest = MemberInterest.builder()
                    .member(member)
                    .interestCategory(InterestCategory.TYPE)
                    .interestCode(type)
                    .build();
                memberInterestRepository.save(memberInterest);
            });
        }

        // 관심 장르 저장
        if (genreList != null) {
            genreList.forEach(genre -> {
                MemberInterest memberInterest = MemberInterest.builder()
                    .member(member)
                    .interestCategory(InterestCategory.GENRE)
                    .interestCode(genre)
                    .build();
                memberInterestRepository.save(memberInterest);
            });
        }
    }

    /**
     * <p>관심 분야 조회</p>
     *
     * @param memberId 회원 ID
     * @return 관심 분야 목록
     */
    public List<InterestCode> getMemberInterest(Long memberId) {
        return memberRepository.selectInterestCodeList(memberId);
    }

    /**
     * <p>관심 분야 삭제</p>
     *
     * @param member 회원 정보
     */
    @Transactional
    public void deleteMemberInterest(Member member) {
        memberInterestRepository.deleteByMember(member);
    }
}
