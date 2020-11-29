package com.eshilov.auth.auth;

import static org.springframework.http.HttpStatus.OK;

import com.eshilov.auth.generated.api.AuthApi;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
        var response = authService.signUp(signUpRequest);
        return ResponseEntity.status(OK).body(response);
    }
}
