package hey.io.heybackend.user.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

    @Schema(description = "아이디", example = "1", required = true)
    private Long id;

    @Schema(description = "닉네임", example = "홍길동", required = true)
    private String nickName;
}
