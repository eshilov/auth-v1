package com.eshilov.auth.auth;

import static com.eshilov.auth.TestDataUtils.*;
import static com.eshilov.auth.generated.api.AuthApi.logInPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import com.eshilov.auth.IntTest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.operations.ApiOperations;
import com.eshilov.auth.operations.AuthOperations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class LogInIntTest extends IntTest {

    @Autowired private AuthOperations authOperations;

    @Autowired private ApiOperations apiOperations;

    @Test
    public void logInHappy() {
        // Given
        var signUpRequest = signUpRequest();
        authOperations.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);

        // When
        var logInResponse = authOperations.logIn(logInRequest);

        // Then
        assertLogInResponse(logInResponse);
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

    private void assertLogInResponse(ResponseEntity<TokenPair> response) {
        assertSoftly(
                softly -> {
                    var code = response.getStatusCode();
                    softly.assertThat(code).isEqualTo(OK);

                    var body = response.getBody();
                    softly.assertThat(body).isNotNull();

                    softly.assertThat(body.getAccess()).isNotNull();
                    softly.assertThat(body.getRefresh()).isNotNull();
                });
    }
}
