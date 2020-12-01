package com.eshilov.auth.auth.operations.signup.validation;

import com.eshilov.auth.generated.model.SignUpRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestValidator {

    private final SignUpRequestUsernameValidator usernameValidator;
    private final SignUpRequestPasswordValidator passwordValidator;

    public void validate(@NonNull SignUpRequest request) {
        usernameValidator.validate(request.getUsername());
        passwordValidator.validate(request.getPassword());
    }
}
