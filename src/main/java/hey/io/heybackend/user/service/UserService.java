package hey.io.heybackend.user.service;

import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.UserResponse;
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

    public UserResponse getUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        return UserResponse.of(user);
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        validateNickName(request.getNickName());

        user.updateUser(request.getNickName(), encoder.encode(request.getPassword()));

        return new UpdateUserResponse(userId, request.getNickName());
    }

    private void validateNickName(String nickName) {
        if(userRepository.existsUserByNickName(nickName)) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
