package com.eshilov.auth.auth.validation.username;

import com.eshilov.auth.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestUsernameValidatorExecutor {

    private final UserService userService;

    public void execute(String username) {
        SignUpRequestUsernameValidator.builder()
                .userService(userService)
                .username(username)
                .build()
                .validate();
    }
}
