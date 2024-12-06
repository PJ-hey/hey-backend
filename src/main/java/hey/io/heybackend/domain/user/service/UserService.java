package hey.io.heybackend.domain.user.service;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.exception.notfound.EntityNotFoundException;
import hey.io.heybackend.common.exception.unauthorized.UnAuthorizedException;
import hey.io.heybackend.common.jwt.JwtTokenProvider;
import hey.io.heybackend.domain.member.entity.Member;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import hey.io.heybackend.domain.user.dto.TokenDto;
import hey.io.heybackend.domain.user.dto.UserRequest;
import hey.io.heybackend.domain.user.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtTokenProvider jwtTokenProvider;

  private final TokenRepository tokenRepository;
  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder;


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
