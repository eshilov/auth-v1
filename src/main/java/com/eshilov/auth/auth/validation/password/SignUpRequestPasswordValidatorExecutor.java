package com.eshilov.auth.auth.validation.password;

import org.springframework.stereotype.Component;

@Component
public class SignUpRequestPasswordValidatorExecutor {

    public void execute(String password) {
        SignUpRequestPasswordValidator.builder().password(password).build().validate();
    }
}
