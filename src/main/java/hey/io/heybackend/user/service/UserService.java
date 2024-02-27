package hey.io.heybackend.user.service;

import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.dtos.response.UserResponse;
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

    @Transactional
    public User createUser(CreateUserRequest request) {
        try {
            String hashedPassword = encoder.encode(request.getPassword());
            User user = new User(request);
            User savedUser = userRepository.save(user);

            return savedUser;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_SAVED_FAILED);
        }

    }

    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.of(user);
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        validateNickName(request.getNickName());

        user.updateUser(request.getNickName(), encoder.encode(request.getPassword()));

        return new UpdateUserResponse(userId, request.getNickName());
    }

    private void validateNickName(String nickName) {
        if (userRepository.existsUserByNickName(nickName)) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NICKNAME);
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}
