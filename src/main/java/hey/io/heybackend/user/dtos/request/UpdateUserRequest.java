package hey.io.heybackend.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String nickName;
    private String password;
    private String email;
    private String provider;
    private String userName;
    private String phoneNumber;

}
