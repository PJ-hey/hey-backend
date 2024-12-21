package hey.io.heybackend.domain.mypage.service;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.exception.notfound.EntityNotFoundException;
import hey.io.heybackend.domain.member.dto.AuthenticatedMember;
import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.enums.InterestCode;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import hey.io.heybackend.domain.member.service.MemberInterestService;
import hey.io.heybackend.domain.mypage.dto.MyPageDto.MemberDetailResponse;
import hey.io.heybackend.domain.mypage.dto.MyPageDto.MemberDetailResponse.MemberInterestDto;
import hey.io.heybackend.domain.mypage.dto.MyPageDto.ModifyMemberRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;

    private final MemberInterestService memberInterestService;

    /**
     * <p>회원 상세</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @return 회원 상세 정보
     */
    public MemberDetailResponse getMemberInfo(AuthenticatedMember authenticatedMember) {

        // 1. 회원 상세 정보 조회
        MemberDetailResponse memberDetail = memberRepository.selectMemberDetail(
            authenticatedMember.getMemberId());
        if (memberDetail == null) {
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 2. 관심 분야 조회
        List<InterestCode> interestCodeList = memberInterestService.getMemberInterest(
            authenticatedMember.getMemberId());

        return MemberDetailResponse.of(memberDetail, MemberInterestDto.of(interestCodeList));
    }

    /**
     * <p>닉네임 중복 확인</p>
     *
     * @param nickname 닉네임
     * @return 닉네임 중복 여부
     */
    public Boolean existsNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * <p>회원 정보 수정</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param modifyMemberRequest 회원 정보
     * @return 회원 ID
     */
    @Transactional
    public Long modifyMember(AuthenticatedMember authenticatedMember,
        ModifyMemberRequest modifyMemberRequest) {

        // 1. 회원 조회
        Member member = memberRepository.findById(authenticatedMember.getMemberId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 닉네임 수정
        member.updateNickname(modifyMemberRequest.getNickname());

        // 3. 관심 분야 삭제
        memberInterestService.deleteMemberInterest(member);

        // 4. 관심 분야 등록
        memberInterestService.insertMemberInterest(member, modifyMemberRequest.getType(),
            modifyMemberRequest.getGenre());

        return member.getMemberId();
    }

}
