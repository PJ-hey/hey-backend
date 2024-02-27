package hey.io.heybackend.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest {
    private String email;

    private String password;

    private String userName;

    private String phoneNumber;

    private String nickName;
}
