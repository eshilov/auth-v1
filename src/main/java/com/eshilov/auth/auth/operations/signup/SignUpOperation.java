package com.eshilov.auth.auth.operations.signup;

import com.eshilov.auth.auth.operations.signup.validation.SignUpRequestValidator;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.generated.model.User;
import com.eshilov.auth.token.TokenService;
import com.eshilov.auth.token.operations.create.CreateTokenPairParams;
import com.eshilov.auth.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpOperation {

    private final SignUpRequestValidator requestValidatorExecutor;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    public SignUpResponse execute(SignUpRequest request) {
        requestValidatorExecutor.validate(request);

        var username = request.getUsername();
        var encodedPassword = passwordEncoder.encode(request.getPassword());
        var userToCreate = User.builder().username(username).password(encodedPassword).build();
        var createdUser = userService.createUser(userToCreate);
        var tokenPair = createTokenPairForUser(createdUser);
        return new SignUpResponse(createdUser, tokenPair);
    }

    private TokenPair createTokenPairForUser(User user) {
        var createTokenPairParams =
                CreateTokenPairParams.builder().subject(user.getUsername()).build();
        return tokenService.createTokenPair(createTokenPairParams);
    }
}
