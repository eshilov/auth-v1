package com.eshilov.auth.auth;

import com.eshilov.auth.generated.model.LogInRequest;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import com.eshilov.auth.generated.model.TokenPair;

public interface AuthService {

    SignUpResponse signUp(SignUpRequest request);

    TokenPair logIn(LogInRequest request);
}
