package hey.io.heybackend.domain.member.service;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.exception.notfound.EntityNotFoundException;
import hey.io.heybackend.domain.member.dto.AuthenticatedMember;
import hey.io.heybackend.domain.member.dto.MemberInterestRequest;
import hey.io.heybackend.domain.member.dto.MemberTermsRequest;
import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.enums.MemberStatus;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberInterestService memberInterestService;

    /**
     * <p>약관 동의</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param memberTermsRequest  약관 동의 여부
     * @return 회원 ID
     */
    @Transactional
    public Long modifyMemberTerms(AuthenticatedMember authenticatedMember,
        MemberTermsRequest memberTermsRequest) {

        Member member = memberRepository.findById(authenticatedMember.getMemberId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        member.updateBasicTermsAgreed(memberTermsRequest.isBasicTermsAgreed());

        if (memberTermsRequest.isBasicTermsAgreed()) {
            member.updateMemberStatus(MemberStatus.ACTIVE);
        }

        return member.getMemberId();

    }

    /**
     * <p>관심 분야 등록</p>
     *
     * @param authenticatedMember   인증 회원 정보
     * @param memberInterestRequest 관심 정보 목록
     * @return 회원 ID
     */
    @Transactional
    public Long createMemberInterest(AuthenticatedMember authenticatedMember,
        MemberInterestRequest memberInterestRequest) {

        // 1. 회원 조회
        Member member = memberRepository.findById(authenticatedMember.getMemberId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 관심 분야 삭제
        memberInterestService.deleteMemberInterest(member);

        // 3. 관심 분야 등록
        memberInterestService.insertMemberInterest(member, memberInterestRequest.getType(),
            memberInterestRequest.getGenre());

        return member.getMemberId();
    }

}