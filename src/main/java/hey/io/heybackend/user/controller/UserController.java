package hey.io.heybackend.user.controller;

import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.UserResponse;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponse>> getUser(@PathVariable("id") Long userId) {

        UserResponse userResponse = userService.getUser(userId);
        ResponseDTO<UserResponse> responseDTO = new ResponseDTO<>(true, Optional.of(userResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UpdateUserResponse>> updateUser(@PathVariable("id") Long userId, @RequestBody UpdateUserRequest request) {

        UpdateUserResponse updateUserResponse = userService.updateUser(userId, request);
        ResponseDTO<UpdateUserResponse> responseDTO = new ResponseDTO<>(true, Optional.of(updateUserResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable("id") Long userId) {

        userService.deleteUser(userId);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
