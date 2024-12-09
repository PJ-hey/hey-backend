package hey.io.heybackend.domain.user.service;

import hey.io.heybackend.common.config.component.AvailableRoleHierarchy;
import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.exception.notfound.EntityNotFoundException;
import hey.io.heybackend.common.exception.unauthorized.UnAuthorizedException;
import hey.io.heybackend.common.jwt.JwtTokenProvider;
import hey.io.heybackend.domain.user.dto.TokenDto;
import hey.io.heybackend.domain.user.dto.UserRequest;
import hey.io.heybackend.domain.user.entity.UserAuth;
import hey.io.heybackend.domain.user.repository.TokenRepository;
import hey.io.heybackend.domain.user.repository.UserAuthRepository;
import hey.io.heybackend.domain.member.dto.AuthenticatedMember;
import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final UserAuthRepository userAuthRepository;
    private final TokenRepository tokenRepository;

    private final AvailableRoleHierarchy availableRoleHierarchy;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    /**
     * <p>사용자 정보 조회</p>
     *
     * @param userId 사용자 아이디
     * @return MemberDto
     */
    @Override
    public AuthenticatedMember loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        List<GrantedAuthority> authorities = loadUserAuthorities(userId);

        return AuthenticatedMember.of(member, authorities);
    }

    /**
     * <p>사용자 권한 정보 조회</p>
     *
     * @param userId 사용자 아이디
     * @return 사용자 권한 목록
     */
    public List<GrantedAuthority> loadUserAuthorities(String userId) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자별 권한 조회
        List<String> userAuthList = memberRepository.selectUserAuthList(Long.valueOf(userId));
        userAuthList.forEach(authId -> authorities.add(new SimpleGrantedAuthority(authId)));

        // 연결된 모든 하위 계층 권한 포함
        return availableRoleHierarchy.getReachableAuthorities(authorities);
    }

    /**
     * <p>사용자 권한 목록 조회</p>
     *
     * @param member 사용자
     * @return 사용자 권한 목록
     */
    public List<SimpleGrantedAuthority> getAuthorities(Member member) {
        String userId = String.valueOf(member.getMemberId());
        List<UserAuth> userAuthList = userAuthRepository.findByUserId(userId);

        return userAuthList.stream()
            .map(userAuth -> new SimpleGrantedAuthority(userAuth.getAuth().getAuthId()))
            .collect(Collectors.toList());
    }

    public Member getMemberByRefreshToken(String refreshToken) {
        return memberRepository.selectMemberByRefreshToken(refreshToken).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * <p>사용자 정보로 JWT 토큰 발급</p>
     *
     * @param userRequest 로그인 사용자 정보
     * @return 발급된 토큰 정보
     */
    @Transactional
    public TokenDto getAccessToken(UserRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        // 사용자 정보 검증
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND, "email: " + email));


        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new UnAuthorizedException(ErrorCode.INCORRECT_USER);
        }

        // JWT 토큰 생성
        TokenDto tokenDTO = jwtTokenProvider.createToken(member);
        tokenRepository.save(tokenDTO.toToken());

        return tokenDTO;
    }

}
