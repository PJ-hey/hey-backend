package hey.io.heybackend.user.entities;

import hey.io.heybackend.common.entities.CommonModel;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
@EntityListeners(AuditingEntityListener.class)
public class User extends CommonModel {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "provider")
    private String provider;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(name = "is_completed")
    @ColumnDefault("false")
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(CreateUserRequest request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.userName = request.getUserName();
        this.phoneNumber = request.getPhoneNumber();
        this.nickName = request.getNickName();
        this.provider = request.getProvider();
        this.isCompleted = request.isCompleted();
    }

}
