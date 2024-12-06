package hey.io.heybackend.domain.login.mapper;

import hey.io.heybackend.domain.login.dto.SocialUserInfo;

import java.text.ParseException;
import java.util.Map;

public interface UserInfoMapper {
    SocialUserInfo mapUserInfo(Map<String, Object> userInfo) throws ParseException;
}
