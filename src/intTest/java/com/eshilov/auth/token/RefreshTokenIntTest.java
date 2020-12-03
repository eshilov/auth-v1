package com.eshilov.auth.token;

import static com.eshilov.auth.common.TestDataUtils.logInRequest;
import static com.eshilov.auth.common.TestDataUtils.refreshRequest;
import static com.eshilov.auth.common.TestDataUtils.signUpRequest;
import static com.eshilov.auth.generated.api.TokenApi.refreshTokenPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.eshilov.auth.auth.AuthOperations;
import com.eshilov.auth.common.ApiOperations;
import com.eshilov.auth.common.IntTest;
import com.eshilov.auth.common.token.TestTokenHelper;
import com.eshilov.auth.common.token.TokenPairResponseEntityAssertion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("ConstantConditions")
public class RefreshTokenIntTest extends IntTest {

    @Autowired private AuthOperations authOperations;

    @Autowired private TokenOperations tokenOperations;

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
        var refreshResponseEntity = tokenOperations.refreshToken(refreshRequest);

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
                apiOperations.postForServletResponse(refreshTokenPath, expiredTokenRefreshRequest);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    public void refreshBadToken() {
        // Given
        var badTokenRequest = refreshRequest("bad token");

        // When
        var response = apiOperations.postForServletResponse(refreshTokenPath, badTokenRequest);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }
}
