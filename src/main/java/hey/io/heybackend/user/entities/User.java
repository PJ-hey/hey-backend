package hey.io.heybackend.user.entities;

import hey.io.heybackend.common.entities.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String userName;

    private String phoneNumber;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;


    public void updateUser(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

}
