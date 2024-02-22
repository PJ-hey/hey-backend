package hey.io.heybackend.user.service;

import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.MyInfoResponse;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    // TODO : 에러 정의

    public MyInfoResponse getMyInfo(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("USER_NOT_FOUND"));
        return MyInfoResponse.of(user);
    }

    @Transactional
    public UpdateUserResponse updateUserInfo(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("USER_NOT_FOUND"));

        validateNickName(request.getNickName());
        validatePassword(userId, request.getCurrentPassword());

        user.updateUser(request.getNickName(), encoder.encode(request.getNewPassword()));

        return new UpdateUserResponse(userId);
    }

    private void validateNickName(String nickName) {
        if(userRepository.existsUserByNickName(nickName)) {
            throw new IllegalArgumentException();
        }
    }

    private void validatePassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("USER_NOT_FOUND"));

        if(!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException();
        }
    }

}
