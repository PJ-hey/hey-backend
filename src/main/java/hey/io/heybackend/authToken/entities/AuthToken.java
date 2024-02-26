package hey.io.heybackend.authToken.entities;

import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table
@EntityListeners(AuditingEntityListener.class)
public class AuthToken extends CommonModel {

    @Column
    private String email;

    @Column
    private UUID uuid;

    @Column
    private String verificationCode;

    @Column
    private int attemptedCount;

    @Column
    private OffsetDateTime expiredAt;

}
