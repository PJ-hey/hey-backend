package hey.io.heybackend.domain.member.repository;


import hey.io.heybackend.domain.auth.enums.AuthId;
import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.enums.InterestCode;
import hey.io.heybackend.domain.mypage.dto.MyPageDto.MemberDetailResponse;
import java.util.List;
import java.util.Optional;

public interface MemberQueryRepository {


    /**
     * <p>회원 정보</p>
     *
     * @param providerUid 소셜 ID
     * @return 회원
     */
    Optional<Member> selectMemberByProviderUid(String providerUid);

    /**
     * <p>회원 정보</p>
     *
     * @param refreshToken 리프레시 토큰
     * @return 회원
     */
    Optional<Member> selectMemberByRefreshToken(String refreshToken);

    /**
     * <p>회원 상세</p>
     *
     * @param memberId 회원 ID
     * @return 회원 상세 정보 + 관심 정보
     */
    MemberDetailResponse selectMemberDetail(Long memberId);

    /**
     * <p>사용자 권한 정보</p>
     *
     * @param memberId 사용자 ID
     * @return 사용자 권한 목록
     */
    List<AuthId> selectUserAuthList(Long memberId);


    /**
     * <p>관심 정보 목록</p>
     *
     * @param memberId 회원 ID
     * @return 관심 정보 목록
     */
    List<InterestCode> selectInterestCodeList(Long memberId);

}
