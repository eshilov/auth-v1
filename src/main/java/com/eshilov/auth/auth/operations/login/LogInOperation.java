package com.eshilov.auth.auth.operations.login;

import static com.eshilov.auth.utils.ExceptionUtils.validationExceptionSupplier;

import com.eshilov.auth.auth.operations.login.validation.LogInRequestValidator;
import com.eshilov.auth.generated.model.LogInRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.tokens.TokenService;
import com.eshilov.auth.tokens.operations.create.CreateTokensRequest;
import com.eshilov.auth.users.UserService;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogInOperation {

    private static final String BAD_CREDENTIALS_ERROR_MESSAGE = "Bad credentials";

    private final LogInRequestValidator requestValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    public TokenPair execute(@NonNull LogInRequest request) {
        requestValidator.validate(request);
        var username = request.getUsername();
        var user =
                userService
                        .findUserByUsername(username)
                        .orElseThrow(badCredentialsExceptionSupplier());
        var encodedDbPassword = user.getPassword();
        var encodedRequestPassword = passwordEncoder.encode(request.getPassword());
        var passwordsMatch = StringUtils.equals(encodedDbPassword, encodedRequestPassword);
        if (passwordsMatch) {
            return createTokenPairForUsername(username);
        } else {
            throw badCredentialsExceptionSupplier().get();
        }
    }

    private TokenPair createTokenPairForUsername(String username) {
        var params = CreateTokensRequest.builder().subject(username).build();
        return tokenService.createTokens(params);
    }

    private Supplier<RuntimeException> badCredentialsExceptionSupplier() {
        return validationExceptionSupplier(BAD_CREDENTIALS_ERROR_MESSAGE);
    }
}
