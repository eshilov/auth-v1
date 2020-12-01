package com.eshilov.auth.auth;

import com.eshilov.auth.auth.operations.signup.SignUpOperation;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SignUpOperation signUpOperation;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        return signUpOperation.execute(request);
    }
}
