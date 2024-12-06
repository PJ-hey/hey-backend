package hey.io.heybackend.domain.login.mapper;

import hey.io.heybackend.domain.member.enums.Provider;
import hey.io.heybackend.domain.login.dto.SocialUserInfo;

import java.util.Map;

public class GoogleUserInfoMapper implements UserInfoMapper {
    @Override
    public SocialUserInfo mapUserInfo(Map<String, Object> userInfo) {
        return new SocialUserInfo(
                (String) userInfo.get("email"),
                (String) userInfo.get("name"),
                Provider.GOOGLE,
                (String) userInfo.get("sub")
                );
    }
}

