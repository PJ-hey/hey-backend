package hey.io.heybackend.user.entities;

import hey.io.heybackend.common.entities.CommonModel;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
@EntityListeners(AuditingEntityListener.class)
public class User extends CommonModel {

    private String email;

    private String password;

    private String userName;

    private String phoneNumber;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(CreateUserRequest request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.userName = request.getUserName();
        this.phoneNumber = request.getPhoneNumber();
        this.nickName = request.getNickName();
    }

    public void updateUser(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

}
