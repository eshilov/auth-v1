package com.eshilov.auth.auth.operations.login.validation;

import static com.eshilov.auth.utils.ExceptionUtils.throwValidationException;

import com.eshilov.auth.generated.model.LogInRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogInRequestValidator {

    private static final int DEFAULT_STRING_MAX_LENGTH = 255;
    private static final String USERNAME_LABEL = "Username";
    private static final String PASSWORD_LABEL = "Password";

    public void validate(@NonNull LogInRequest request) {
        validateDefaultStringAttribute(request.getUsername(), USERNAME_LABEL);
        validateDefaultStringAttribute(request.getPassword(), PASSWORD_LABEL);
    }

    private void validateDefaultStringAttribute(@NonNull String value, String label) {
        if (value.length() > DEFAULT_STRING_MAX_LENGTH) {
            throwValidationException(
                    String.format(
                            "%s length must not be greater than %d",
                            label, DEFAULT_STRING_MAX_LENGTH));
        }
    }
}
