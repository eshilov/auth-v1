package com.eshilov.auth.auth;

import static com.eshilov.auth.common.TestDataUtils.*;
import static com.eshilov.auth.generated.api.AuthApi.logInPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.eshilov.auth.common.ApiOperations;
import com.eshilov.auth.common.IntTest;
import com.eshilov.auth.common.token.TokenPairResponseEntityAssertion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LogInIntTest extends IntTest {

    @Autowired private AuthOperations authOperations;

    @Autowired private ApiOperations apiOperations;

    @Autowired private TokenPairResponseEntityAssertion tokenPairResponseEntityAssertion;

    @Test
    public void logInHappy() {
        // Given
        var signUpRequest = signUpRequest();
        authOperations.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);

        // When
        var logInResponseEntity = authOperations.logIn(logInRequest);

        // Then
        tokenPairResponseEntityAssertion.execute(logInResponseEntity, logInRequest.getUsername());
    }

    @Test
    public void logInBadUsername() {
        // Given
        var signUpRequest = signUpRequest();
        authOperations.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);
        logInRequest.setUsername(username());

        // When
        var logInResponse = apiOperations.postForServletResponse(logInPath, logInRequest);

        // Then
        assertThat(logInResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    public void logInBadPassword() {
        // Given
        var signUpRequest = signUpRequest();
        authOperations.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);
        logInRequest.setPassword("bad password");

        // When
        var logInResponse = apiOperations.postForServletResponse(logInPath, logInRequest);

        // Then
        assertThat(logInResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
    }
}
