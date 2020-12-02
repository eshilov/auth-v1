package com.eshilov.auth.auth;

import static com.eshilov.auth.TestDataUtils.signUpRequest;
import static com.eshilov.auth.generated.api.AuthApi.signUpPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import com.eshilov.auth.operations.ApiOperations;
import com.eshilov.auth.operations.AuthOperations;
import com.eshilov.auth.IntTest;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class SignUpIntTest extends IntTest {

    @Autowired private AuthOperations authOperations;

    @Autowired private ApiOperations apiOperations;

    @Test
    public void signUpHappy() {
        // Given
        var request = signUpRequest();

        // When
        var response = authOperations.signUp(request);

        // Then
        assertSignUpResponse(response, request);
    }

    @Test
    public void signUpUsernameTaken() {
        // Given
        var initialRequest = signUpRequest();
        authOperations.signUp(initialRequest);

        var username = initialRequest.getUsername();
        var request = signUpRequest();
        request.setUsername(username);

        // When
        var response = apiOperations.postForServletResponse(signUpPath, request);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    public void signUpBadUsername() {
        // Given
        var request = signUpRequest();
        var badUsername = "";
        request.setUsername(badUsername);

        // When
        var response = apiOperations.postForServletResponse(signUpPath, request);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    public void signUpBadPassword() {
        // Given
        var request = signUpRequest();
        var badPassword = "";
        request.setPassword(badPassword);

        // When
        var response = apiOperations.postForServletResponse(signUpPath, request);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    private void assertSignUpResponse(
            ResponseEntity<SignUpResponse> responseEntity, SignUpRequest request) {
        assertSoftly(
                softly -> {
                    var status = responseEntity.getStatusCode();
                    softly.assertThat(status).isEqualTo(OK);
                    var response = responseEntity.getBody();
                    softly.assertThat(response).isNotNull();

                    var user = response.getUser();
                    softly.assertThat(user.getId()).isNotNull();
                    softly.assertThat(user.getUsername()).isEqualTo(request.getUsername());

                    var tokens = response.getTokens();
                    softly.assertThat(tokens.getAccess()).isNotBlank();
                    softly.assertThat(tokens.getRefresh()).isNotBlank();
                });
    }
}
