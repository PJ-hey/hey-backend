package hey.io.heybackend.user.dtos.response;

import hey.io.heybackend.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoResponse {

    private Long id;
    private String email;
    private String userName;
    private String phoneNumber;
    private String nickName;

    public static MyInfoResponse of(User user) {
        return new MyInfoResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPhoneNumber(), user.getNickName());
    }
}
