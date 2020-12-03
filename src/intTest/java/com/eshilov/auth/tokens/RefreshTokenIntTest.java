package com.eshilov.auth.tokens;

import static com.eshilov.auth.generated.api.TokensApi.refreshTokensPath;
import static com.eshilov.auth.testhelp.TestDataUtils.logInRequest;
import static com.eshilov.auth.testhelp.TestDataUtils.refreshRequest;
import static com.eshilov.auth.testhelp.TestDataUtils.signUpRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.eshilov.auth.auth.TestAuthApi;
import com.eshilov.auth.testhelp.ApiOperations;
import com.eshilov.auth.testhelp.IntTest;
import com.eshilov.auth.testhelp.tokens.TestTokenHelper;
import com.eshilov.auth.testhelp.tokens.TokenPairResponseEntityAssertion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("ConstantConditions")
public class RefreshTokenIntTest extends IntTest {

    @Autowired private TestAuthApi testAuthApi;

    @Autowired private TestTokensApi testTokensApi;

    @Autowired private ApiOperations apiOperations;

    @Autowired private TokenPairResponseEntityAssertion tokenPairResponseEntityAssertion;

    @Autowired private TestTokenHelper testTokenHelper;

    @Test
    public void refreshHappy() {
        // Given
        var signUpRequest = signUpRequest();
        testAuthApi.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);
        var logInResponseEntity = testAuthApi.logIn(logInRequest);

        var refreshToken = logInResponseEntity.getBody().getRefresh();
        var refreshRequest = refreshRequest(refreshToken);

        // When
        var refreshResponseEntity = testTokensApi.refreshTokens(refreshRequest);

        // Then
        tokenPairResponseEntityAssertion.execute(refreshResponseEntity, logInRequest.getUsername());
    }

    @Test
    public void refreshExpiredToken() {
        // Given
        var signUpRequest = signUpRequest();
        testAuthApi.signUp(signUpRequest);

        var logInRequest = logInRequest(signUpRequest);
        var logInResponseEntity = testAuthApi.logIn(logInRequest);

        var originalRefreshToken = logInResponseEntity.getBody().getRefresh();
        var expiredRefreshToken = testTokenHelper.makeTokenExpired(originalRefreshToken);
        var expiredTokenRefreshRequest = refreshRequest(expiredRefreshToken);

        // When
        var response =
                apiOperations.postForServletResponse(refreshTokensPath, expiredTokenRefreshRequest);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    public void refreshBadToken() {
        // Given
        var badTokenRequest = refreshRequest("bad token");

        // When
        var response = apiOperations.postForServletResponse(refreshTokensPath, badTokenRequest);

        // Then
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }
}
