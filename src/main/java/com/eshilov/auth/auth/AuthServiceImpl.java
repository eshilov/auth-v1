package com.eshilov.auth.auth;

import com.eshilov.auth.auth.operations.login.LogInOperation;
import com.eshilov.auth.auth.operations.signup.SignUpOperation;
import com.eshilov.auth.generated.model.LogInRequest;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import com.eshilov.auth.generated.model.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SignUpOperation signUpOperation;
    private final LogInOperation logInOperation;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        return signUpOperation.execute(request);
    }

    @Override
    public TokenPair logIn(LogInRequest request) {
        return logInOperation.execute(request);
    }
}
