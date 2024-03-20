package hey.io.heybackend.user.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Schema(description = "닉네임", example = "홍길동", required = true)
    private String nickName;

    @Schema(description = "비밀번호", example = "abc!1234", required = true)
    private String password;


}
