package com.eshilov.auth.auth;

import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;

public interface AuthService {

    SignUpResponse signUp(SignUpRequest request);
}
