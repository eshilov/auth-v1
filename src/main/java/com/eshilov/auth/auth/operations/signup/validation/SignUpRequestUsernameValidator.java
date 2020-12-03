package com.eshilov.auth.auth.operations.signup.validation;

import static com.eshilov.auth.utils.ExceptionUtils.throwValidationException;

import com.eshilov.auth.users.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestUsernameValidator {

    private final UserService userService;

    public void validate(@NonNull String username) {
        validateFormat(username);
        validateUniqueness(username);
    }

    private void validateFormat(String username) {
        if (isInvalidFormat(username)) {
            throwValidationException("Username has invalid format");
        }
    }

    private boolean isInvalidFormat(String username) {
        return !EmailValidator.getInstance().isValid(username);
    }

    private void validateUniqueness(String username) {
        if (isUsernameAlreadyTaken(username)) {
            throwValidationException(String.format("Username %s is already taken", username));
        }
    }

    private boolean isUsernameAlreadyTaken(String username) {
        return userService.findUserByUsername(username).isPresent();
    }
}
