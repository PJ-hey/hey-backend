package hey.io.heybackend.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST: 잘못된 요청 */
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
    TOKEN_ATTEMPTED_COUNT_EXCEED(HttpStatus.BAD_REQUEST, "하루에 인증할 수 있는 최대 허용 수를 넘었습니다."),

    /* 401 UNAUTHORIZED: 인증되지 않음*/
    TOKEN_VERIFY_FAILED(HttpStatus.UNAUTHORIZED, "인증번호가 일치하지 않습니다."),

    /* 404 NOT_FOUND: Resource를 찾을 수 없음 */
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),

    /* 409 CONFLICT: 리소스 요청 충돌 */
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),

    /* 500 INTERNAL_SERVER_ERROR: 서버 에러가 발생함 */
    TOKEN_SAVED_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 저장에 실패했습니다."),
    INVALID_UUID_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "UUID가 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
