package com.eshilov.auth.operations;

import com.eshilov.auth.generated.api.AuthApi;
import com.eshilov.auth.generated.model.LogInRequest;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import com.eshilov.auth.generated.model.TokenPair;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthOperations implements AuthApi {

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
