package com.eshilov.auth.auth.validation.username;

import static com.eshilov.auth.utils.ExceptionUtils.throwValidationException;

import com.eshilov.auth.user.UserService;
import lombok.Builder;
import org.apache.commons.validator.routines.EmailValidator;

@Builder
public class SignUpRequestUsernameValidator {

    private final UserService userService;

    private final String username;

    public void validate() {
        validateFormat();
        validateUniqueness();
    }

    private void validateFormat() {
        if (isInvalidFormat()) {
            throwValidationException("Username has invalid format");
        }
    }

    private boolean isInvalidFormat() {
        return !EmailValidator.getInstance().isValid(username);
    }

    private void validateUniqueness() {
        if (isUsernameAlreadyTaken()) {
            throwValidationException(String.format("Username %s is already taken", username));
        }
    }

    private boolean isUsernameAlreadyTaken() {
        return userService.findUserByUsername(username).isPresent();
    }
}
