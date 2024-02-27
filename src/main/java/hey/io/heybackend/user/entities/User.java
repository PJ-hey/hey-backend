package hey.io.heybackend.user.entities;

import hey.io.heybackend.common.entities.CommonModel;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
public class User extends CommonModel {

    private String email;

    private String password;

    private String userName;

    private String phoneNumber;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(CreateUserRequest request) {
        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        this.email = request.getEmail();
        this.password = hashedPassword;
        this.userName = request.getUserName();
        this.phoneNumber = request.getPhoneNumber();
        this.nickName = request.getNickName();
    }

    public void updateUser(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

}
