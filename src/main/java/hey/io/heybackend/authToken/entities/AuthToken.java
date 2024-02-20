package hey.io.heybackend.authToken.entities;

import hey.io.heybackend.common.entities.CommonModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AuthToken extends CommonModel {

    @Column
    private UUID uuid;

    @Column
    private String verificationCode;

    @Column
    private OffsetDateTime expiredAt;

}
