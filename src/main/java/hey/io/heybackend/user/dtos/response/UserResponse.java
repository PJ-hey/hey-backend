package hey.io.heybackend.user.dtos.response;

import hey.io.heybackend.user.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "아이디", example = "1", required = true)
    private Long id;

    @Schema(description = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @Schema(description = "이름", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "휴대폰", example = "01012345678", required = true)
    private String phoneNumber;

    @Schema(description = "닉네임", example = "홍길동", required = true)
    private String nickName;

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPhoneNumber(), user.getNickName());
    }
}