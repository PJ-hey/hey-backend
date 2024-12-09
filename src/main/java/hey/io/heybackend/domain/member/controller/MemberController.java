package hey.io.heybackend.domain.member.controller;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.response.ApiResponse;
import hey.io.heybackend.common.swagger.ApiErrorCode;
import hey.io.heybackend.common.swagger.ApiErrorCodes;
import hey.io.heybackend.domain.auth.dto.AuthenticatedMember;
import hey.io.heybackend.domain.member.dto.*;
import hey.io.heybackend.domain.member.dto.MemberDto.MemberDetailResponse;
import hey.io.heybackend.domain.member.dto.MemberDto.MemberTermsRequest;
import hey.io.heybackend.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "4. Member", description = "회원 관련 API")
@SecurityRequirement(name = "Bearer Authentication")
public class MemberController {

    private final MemberService memberService;

    /**
     * <p>약관 동의 수정</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param memberTermsRequest 약관 동의 정보
     * @return 회원 ID
     */
    @PutMapping("/member/terms")
    @ApiErrorCode(ErrorCode.MEMBER_NOT_FOUND)
    @Operation(summary = "약관 동의", description = "약관 동의 정보를 수정합니다.")
    public ApiResponse<Long> modifyMemberTerms(@AuthenticationPrincipal AuthenticatedMember authenticatedMember,
                                                  @RequestBody @Valid MemberTermsRequest memberTermsRequest) {
        return ApiResponse.success(memberService.modifyMemberTerms(authenticatedMember, memberTermsRequest));
    }

    /**
     * <p>관심 정보 등록</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param memberInterestRequest 관심 정보
     * @return 회원 ID
     */
    @PostMapping("/member/interests")
    @ApiErrorCode(ErrorCode.MEMBER_NOT_FOUND)
    @Operation(summary = "관심 정보", description = "관심 정보를 생성합니다.")
    public ApiResponse<Long> createMemberInterest(@AuthenticationPrincipal AuthenticatedMember authenticatedMember,
                                                  @RequestBody @Valid MemberDto.MemberInterestRequest memberInterestRequest) {
        return ApiResponse.created(memberService.createMemberInterest(authenticatedMember, memberInterestRequest));
    }

    /**
     * <p>회원 정보 조회</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @return 회원 정보
     */
    @GetMapping("/mypage/info")
    @ApiErrorCode(ErrorCode.MEMBER_NOT_FOUND)
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    public ApiResponse<MemberDetailResponse> getMemberInfo(@AuthenticationPrincipal AuthenticatedMember authenticatedMember) {
        return ApiResponse.success(memberService.getMemberInfo(authenticatedMember));
    }

    /**
     * <p>닉네임 중복 확인</p>
     *
     * @param nickname 닉네임
     * @return 닉네임 중복 여부
     */
    @GetMapping("/mypage/info/nickname")
    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복을 확인합니다.")
    public ApiResponse<Boolean> existsNickname(@RequestParam("nickname") String nickname) {
        return ApiResponse.success(memberService.existsNickname(nickname));
    }

    /**
     * <p>회원 정보 수정</p>
     *
     * @param authenticatedMember 인증 회원 정보
     * @param modifyMemberRequest 회원 정보
     * @return 회원 ID
     */
    @PutMapping("/mypage/info")
    @ApiErrorCode(ErrorCode.MEMBER_NOT_FOUND)
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    public ApiResponse<Long> modifyMember(@AuthenticationPrincipal AuthenticatedMember authenticatedMember,
                                          @RequestBody @Valid MemberDto.ModifyMemberRequest modifyMemberRequest) {
        return ApiResponse.success(memberService.modifyMember(authenticatedMember, modifyMemberRequest));
    }
}
