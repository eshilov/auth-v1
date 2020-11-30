package com.eshilov.auth.auth.validation;

import com.eshilov.auth.auth.validation.password.SignUpRequestPasswordValidatorExecutor;
import com.eshilov.auth.auth.validation.username.SignUpRequestUsernameValidatorExecutor;
import com.eshilov.auth.generated.model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestValidatorExecutor {

    private final SignUpRequestUsernameValidatorExecutor usernameValidatorExecutor;
    private final SignUpRequestPasswordValidatorExecutor passwordValidatorExecutor;

    public void execute(SignUpRequest request) {
        SignUpRequestValidator.builder()
                .usernameValidatorExecutor(usernameValidatorExecutor)
                .passwordValidatorExecutor(passwordValidatorExecutor)
                .request(request)
                .build()
                .validate();
    }
}
