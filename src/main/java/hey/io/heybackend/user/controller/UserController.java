package hey.io.heybackend.user.controller;

import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.MyInfoResponse;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<MyInfoResponse> getMyInfo(@PathVariable("id") Long userId) {

        return new ResponseEntity<>(userService.getMyInfo(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUserInfo(@PathVariable("id") Long userId, @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(userService.updateUserInfo(userId, request), HttpStatus.OK);
    }
}
