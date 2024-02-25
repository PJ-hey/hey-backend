package hey.io.heybackend.user.dtos.response;

import hey.io.heybackend.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String userName;
    private String phoneNumber;
    private String nickName;

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPhoneNumber(), user.getNickName());
    }
}
