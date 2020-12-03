package com.eshilov.auth.auth;

import static com.eshilov.auth.TestDataUtils.*;
import static com.eshilov.auth.generated.api.AuthApi.refreshPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.eshilov.auth.IntTest;
import com.eshilov.auth.TestTokenHelper;
import com.eshilov.auth.operations.ApiOperations;
import com.eshilov.auth.operations.AuthOperations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("ConstantConditions")
public class RefreshIntTest extends IntTest {

    @Autowired private AuthOperations authOperations;

    @Autowired private ApiOperations apiOperations;

    @Autowired private TokenPairResponseEntityAssertion tokenPairResponseEntityAssertion;

    @Autowired private TestTokenHelper testTokenHelper;

    @Test
    public void refreshHappy() {
        // Given
        var signUpRequest = signUpRequest();
        authOperations.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);
        var logInResponseEntity = authOperations.logIn(logInRequest);

        var refreshToken = logInResponseEntity.getBody().getRefresh();
        var refreshRequest = refreshRequest(refreshToken);

        // When
        var refreshResponseEntity = authOperations.refresh(refreshRequest);

        // Then
        tokenPairResponseEntityAssertion.execute(refreshResponseEntity, logInRequest.getUsername());
    }

    @Test
    public void refreshExpiredToken() {
        // Given
        var signUpRequest = signUpRequest();
        authOperations.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);
        var logInResponseEntity = authOperations.logIn(logInRequest);

        var originalRefreshToken = logInResponseEntity.getBody().getRefresh();
        var expiredRefreshToken = testTokenHelper.makeTokenExpired(originalRefreshToken);
        var expiredTokenRefreshRequest = refreshRequest(expiredRefreshToken);

        // When
        var response =
                apiOperations.postForServletResponse(refreshPath, expiredTokenRefreshRequest);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    public void refreshBadToken() {
        // Given
        var badTokenRequest = refreshRequest("bad token");

        // When
        var response = apiOperations.postForServletResponse(refreshPath, badTokenRequest);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }
}
