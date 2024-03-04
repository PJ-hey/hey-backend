package hey.io.heybackend.oauth.service.attributes;

import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.entities.UserBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        return new UserBuilder()
                .userName((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }),

    KAKAO("kakao", (attributes) -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return new UserBuilder()
                .userName((String) kakaoProfile.get("nickname"))
                .build();
    });

    private final String registrationId;
    private final Function<Map<String, Object>, User> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, User> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static User extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
