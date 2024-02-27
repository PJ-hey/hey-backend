package hey.io.heybackend.user.entities;

import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.user.dtos.request.CreateUserRequest;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserBuilder {
    private String email;

    private String password;

    private String userName;

    private String phoneNumber;

    private String nickName;
    private String provider;
    private boolean isCompleted;

    public UserBuilder email(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new CustomException(ErrorCode.USER_EMAIL_NOT_VALID);
        }
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        if (isValidLength(8, 16, password.length())) {
            throw new CustomException(ErrorCode.USER_PASSWORD_NOT_VALID);
        }
        this.password = new BCryptPasswordEncoder().encode(password);
        return this;
    }

    public UserBuilder userName(String userName) {
        if (isValidLength(2, 20, userName.length())) {
            throw new CustomException(ErrorCode.USER_USERNAME_NOT_VALID);
        }
        this.userName = userName;
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        if (isValidLength(9, 11, phoneNumber.length())) {
            throw new CustomException(ErrorCode.USER_PHONENUMBER_NOT_VALID);
        }
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder provider(String provider) {
        this.provider = provider;
        return this;
    }

    public UserBuilder isCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public UserBuilder nickName(String nickName) {
        if (isValidLength(1, 10, nickName.length())) {
            throw new CustomException(ErrorCode.USER_NICKNAME_NOT_VALID);
        }
        this.nickName = nickName;
        return this;
    }

    public User build() {
        CreateUserRequest request = new CreateUserRequest(email, password, userName, phoneNumber, nickName, provider, isCompleted);
        return new User(request);
    }

    private boolean isValidLength(int min, int max, int length) {
        return length < min || length > max;
    }

}
