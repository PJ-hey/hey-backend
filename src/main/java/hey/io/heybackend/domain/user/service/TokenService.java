package hey.io.heybackend.domain.user.service;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.exception.notfound.EntityNotFoundException;
import hey.io.heybackend.common.exception.unauthorized.UnAuthorizedException;
import hey.io.heybackend.common.jwt.JwtTokenProvider;
import hey.io.heybackend.domain.member.repository.MemberRepository;
import hey.io.heybackend.domain.user.dto.TokenDto;
import hey.io.heybackend.domain.user.dto.UserRequest;
import hey.io.heybackend.domain.user.entity.Token;
import hey.io.heybackend.domain.user.repository.TokenRepository;
import hey.io.heybackend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    private final TokenRepository tokenRepository;



    /**
     * <p>토큰 저장</p>
     *
     * @param member 회원 정보
     * @return 토큰 정보
     */
    @Transactional
    public TokenDto insertToken(Member member) {
        TokenDto tokenDto = jwtTokenProvider.createToken(member);
        tokenRepository.deleteByMemberId(member.getMemberId());

        tokenRepository.saveAndFlush(Token.of(member.getMemberId(), tokenDto.getRefreshToken()));
        return tokenDto;
    }
}
