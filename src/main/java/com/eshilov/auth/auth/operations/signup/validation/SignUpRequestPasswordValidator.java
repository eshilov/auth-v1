package com.eshilov.auth.auth.operations.signup.validation;

import static com.eshilov.auth.utils.ExceptionUtils.throwValidationException;
import static org.apache.commons.lang3.StringUtils.isAlphanumeric;

import java.util.function.IntPredicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestPasswordValidator {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 32;

    public void validate(@NonNull String password) {
        validateNotNull(password);
        validateMinLength(password);
        validateMaxLength(password);
        validateOnlyValidSymbolsPresent(password);
        validateRequiredSymbolsPresent(password);
    }

    private void validateNotNull(String password) {
        if (password == null) {
            throwValidationException("Password must not be null");
        }
    }

    private void validateMinLength(String password) {
        if (password.length() < MIN_LENGTH) {
            throwValidationException(
                    String.format("Password length must not be less than %d", MIN_LENGTH));
        }
    }

    private void validateMaxLength(String password) {
        if (password.length() > MAX_LENGTH) {
            throwValidationException(
                    String.format("Password length must not be greater than %d", MAX_LENGTH));
        }
    }

    private void validateOnlyValidSymbolsPresent(String password) {
        if (!isAlphanumeric(password)) {
            throwValidationException("Password must be an alphanumeric string");
        }
    }

    private void validateRequiredSymbolsPresent(String password) {
        validateAnySymbolMatch(
                password, Character::isLetter, "Password must contain at least one letter");
        validateAnySymbolMatch(
                password, Character::isDigit, "Password must contain at least one digit");
    }

    private void validateAnySymbolMatch(
            String password, IntPredicate predicate, String errorMessage) {
        var noneLetterPresent = password.chars().noneMatch(predicate);
        if (noneLetterPresent) {
            throwValidationException(errorMessage);
        }
    }
}
