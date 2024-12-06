package hey.io.heybackend.domain.login.mapper;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import hey.io.heybackend.domain.member.enums.Provider;
import hey.io.heybackend.domain.login.dto.SocialUserInfo;

import java.text.ParseException;
import java.util.Map;

public class AppleUserInfoMapper implements UserInfoMapper {
    @Override
    public SocialUserInfo mapUserInfo(Map<String, Object> userInfo) throws ParseException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse((String) userInfo.get("id_token"));
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        return new SocialUserInfo(
                claims.getStringClaim("email"),
                claims.getStringClaim("name"),
                Provider.APPLE,
                claims.getSubject()
                );
    }
}

