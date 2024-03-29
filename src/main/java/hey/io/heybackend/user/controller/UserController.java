package hey.io.heybackend.user.controller;

import hey.io.heybackend.artist.dtos.response.ArtistResponse;
import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.user.dtos.request.*;
import hey.io.heybackend.user.dtos.response.FollowArtistResponse;
import hey.io.heybackend.user.dtos.response.FollowShowResponse;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.dtos.response.UserResponse;
import hey.io.heybackend.user.service.UserFollowService;
import hey.io.heybackend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "UserController", description = "프로필 조회/수정/삭제 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserFollowService userFollowService;

    @Tag(name = "UserController")
    @Operation(summary = "프로필 조회 API", description = "프로필 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponse>> getUser(@PathVariable("id") Long userId) {

        UserResponse userResponse = userService.getUser(userId);
        ResponseDTO<UserResponse> responseDTO = new ResponseDTO<>(true, Optional.of(userResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @Tag(name = "UserController")
    @Operation(summary = "프로필 수정 API", description = "프로필 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UpdateUserResponse>> updateUser(@PathVariable("id") Long userId, @RequestBody UpdateUserRequest request) {

        UpdateUserResponse updateUserResponse = userService.updateUser(userId, request);
        ResponseDTO<UpdateUserResponse> responseDTO = new ResponseDTO<>(true, Optional.of(updateUserResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @Tag(name = "UserController")
    @Operation(summary = "회원탈퇴 API", description = "회원탈퇴")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable("id") Long userId) {

        userService.deleteUser(userId);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, Optional.empty());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/followed_show")
    public ResponseEntity<ResponseDTO<FollowShowResponse>> followShow(@RequestBody FollowShowRequest request) {

        FollowShowResponse followShowResponse = userFollowService.followShow(request);
        ResponseDTO<FollowShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(followShowResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/followed_show")
    public ResponseEntity<ResponseDTO<Page<ShowResponse>>> getFollowShow(@RequestBody FollowShowListRequest request, Pageable pageable) {

        Page<ShowResponse> showResponses = userFollowService.getFollowShow(request, pageable);
        ResponseDTO<Page<ShowResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(showResponses));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/followed_artist")
    public ResponseEntity<ResponseDTO<FollowArtistResponse>> followShow(@RequestBody FollowArtistRequest request) {

        FollowArtistResponse followArtistResponse = userFollowService.followArtist(request);
        ResponseDTO<FollowArtistResponse> responseDTO = new ResponseDTO<>(true, Optional.of(followArtistResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/followed_artist")
    public ResponseEntity<ResponseDTO<Page<ArtistResponse>>> getFollowShow(@RequestBody FollowArtistListRequest request, Pageable pageable) {

        Page<ArtistResponse> artistResponses = userFollowService.getFollowArtist(request, pageable);
        ResponseDTO<Page<ArtistResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(artistResponses));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
