package com.eshilov.auth.auth;

import com.eshilov.auth.generated.api.AuthApi;
import com.eshilov.auth.generated.model.*;
import com.eshilov.auth.testhelp.ApiOperations;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestAuthApi implements AuthApi {

    private final ApiOperations apiOperations;

    @Override
    public ResponseEntity<TokenPair> logIn(@Valid LogInRequest logInRequest) {
        return apiOperations.postForResponseEntity(logInPath, logInRequest, TokenPair.class);
    }

    @Override
    public ResponseEntity<SignUpResponse> signUp(@Valid SignUpRequest signUpRequest) {
        return apiOperations.postForResponseEntity(signUpPath, signUpRequest, SignUpResponse.class);
    }
}
