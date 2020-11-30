package com.eshilov.auth.auth.validation.password;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;

import java.util.function.IntPredicate;
import lombok.Builder;

@Builder
public class SignUpRequestPasswordValidator {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 32;

    private final String password;

    public void validate() {
        validateNotNull();
        validateMinLength();
        validateMaxLength();
        validateOnlyValidSymbolsPresent();
        validateRequiredSymbolsPresent();
    }

    private void validateNotNull() {
        if (password == null) {
            throwValidationError("Password must not be null");
        }
    }

    private void validateMinLength() {
        if (password.length() < MIN_LENGTH) {
            throwValidationError(
                    String.format("Password length must not be less than %d", MIN_LENGTH));
        }
    }

    private void validateMaxLength() {
        if (password.length() > MAX_LENGTH) {
            throwValidationError(
                    String.format("Password length must not be greater than %d", MAX_LENGTH));
        }
    }

    private void validateOnlyValidSymbolsPresent() {
        if (!isAlphanumeric(password)) {
            throwValidationError("Password must be an alphanumeric string");
        }
    }

    private void validateRequiredSymbolsPresent() {
        validateAnySymbolMatch(Character::isLetter, "Password must contain at least one letter");
        validateAnySymbolMatch(Character::isDigit, "Password must contain at least one digit");
    }

    private void validateAnySymbolMatch(IntPredicate predicate, String errorMessage) {
        var noneLetterPresent = password.chars().noneMatch(predicate);
        if (noneLetterPresent) {
            throwValidationError(errorMessage);
        }
    }

    private void throwValidationError(String message) {
        throw new IllegalArgumentException(message);
    }
}
