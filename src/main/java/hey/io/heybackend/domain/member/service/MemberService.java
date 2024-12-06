package hey.io.heybackend.domain.member.service;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.exception.notfound.EntityNotFoundException;
import hey.io.heybackend.domain.member.dto.AuthenticatedMember;
import hey.io.heybackend.domain.member.dto.MemberDto.MemberInterestRequest;
import hey.io.heybackend.domain.member.dto.MemberDto.MemberTermsRequest;
import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.entity.MemberInterest;
import hey.io.heybackend.domain.member.repository.MemberInterestRepository;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberInterestRepository memberInterestRepository;

    /**
     * <p>약관 동의 수정</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param memberTermsRequest 약관 동의 정보
     * @return 회원 ID
     */
    @Transactional
    public Long modifyMemberTerms(AuthenticatedMember authenticatedMember, MemberTermsRequest memberTermsRequest) {
        // 1. 회원 조회
        Member member = memberRepository.findById(authenticatedMember.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 약관 동의 여부 수정
        member.updateOptionalTermsAgreed(memberTermsRequest.getBasicTermsAgreed());

        // 3. 회원 상태 수정
        member.updateMemberStatus();
        return member.getMemberId();
    }

    /**
     * <p>관심 정보 등록</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param memberInterestRequest 관심 정보
     * @return 회원 ID
     */
    @Transactional
    public Long createMemberInterest(AuthenticatedMember authenticatedMember, MemberInterestRequest memberInterestRequest) {

        // 1. 회원 조회
        Member member = memberRepository.findById(authenticatedMember.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 관심 정보 삭제
        memberInterestRepository.deleteByMember(member);

        // 3. 관심 정보 등록
        List<MemberInterest> newMemberInterests = memberInterestRequest.toMemberInterests(member);
        memberInterestRepository.saveAll(newMemberInterests);

        return member.getMemberId();
    }

}
