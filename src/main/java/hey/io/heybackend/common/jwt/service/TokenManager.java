package hey.io.heybackend.common.jwt.service;


import hey.io.heybackend.common.exception.BusinessException;
import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.jwt.constant.GrantType;
import hey.io.heybackend.common.jwt.constant.TokenType;
import hey.io.heybackend.common.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    private final String accessTokenExpiredTime;
    private final String refreshTokenExpiredTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long memberId) {
        Date accessTokenExpiredTime = createAccessTokenExpiredTime();
        Date refreshTokenExpiredTime = createRefreshTokenExpiredTime();

        String accessToken = createAccessToken(memberId, accessTokenExpiredTime);
        String refreshToken = createRefreshToken(memberId, refreshTokenExpiredTime);

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireTime(accessTokenExpiredTime)
                .refreshTokenExpireTime(refreshTokenExpiredTime)
                .build();
    }

    public Date createAccessTokenExpiredTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpiredTime));
    }

    public Date createRefreshTokenExpiredTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpiredTime));
    }

    public String createAccessToken(Long memberId, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .claim("memberId", memberId)
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long memberId, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .claim("memberId", memberId)
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();

        return refreshToken;
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.info("token 만료", e);
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new BusinessException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new BusinessException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

}
