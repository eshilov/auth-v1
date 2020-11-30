package com.eshilov.auth.auth.validation;

import com.eshilov.auth.auth.validation.password.SignUpRequestPasswordValidatorExecutor;
import com.eshilov.auth.auth.validation.username.SignUpRequestUsernameValidatorExecutor;
import com.eshilov.auth.generated.model.SignUpRequest;
import lombok.Builder;

@Builder
public class SignUpRequestValidator {

    private final SignUpRequestUsernameValidatorExecutor usernameValidatorExecutor;
    private final SignUpRequestPasswordValidatorExecutor passwordValidatorExecutor;

    private final SignUpRequest request;

    public void validate() {
        usernameValidatorExecutor.execute(request.getUsername());
        passwordValidatorExecutor.execute(request.getPassword());
    }
}
