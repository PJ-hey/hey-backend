package hey.io.heybackend.domain.user.controller;

import hey.io.heybackend.common.exception.ErrorCode;
import hey.io.heybackend.common.response.ApiResponse;
import hey.io.heybackend.common.swagger.ApiErrorCode;
import hey.io.heybackend.domain.user.dto.TokenDto;
import hey.io.heybackend.domain.user.dto.UserRequest;
import hey.io.heybackend.domain.user.service.TokenService;
import hey.io.heybackend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "0. User", description = "사용자 관련 API")
public class UserController {

  private final UserService userService;

  /**
   * <p>JWT 토큰 발급</p>
   *
   * @param userRequest 로그인 사용자 정보
   * @return 발급된 token 정보
   */
  @Operation(summary = "JWT 토큰 발급", description = "사용자 정보를 확인하고 토큰을 발급합니다.")
  @ApiErrorCode(ErrorCode.UNAUTHORIZED)
  @PostMapping("/access")
  public ApiResponse<TokenDto> getAccessToken(@RequestBody @Valid UserRequest userRequest) {
    return ApiResponse.success(userService.getAccessToken(userRequest));
  }

}
