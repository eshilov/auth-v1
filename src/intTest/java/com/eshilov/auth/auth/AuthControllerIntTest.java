package com.eshilov.auth.auth;

import static com.eshilov.auth.TestDataUtils.signUpRequest;
import static com.eshilov.auth.generated.api.AuthApi.signUpPath;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eshilov.auth.BaseIntTest;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.eshilov.auth.generated.model.SignUpResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

public class AuthControllerIntTest extends BaseIntTest {

    @Test
    public void signUpHappy() {
        // Given
        var request = signUpRequest();

        // When
        var response = signUp(request);

        // Then
        assertSignUpResponse(response, request);
    }

    @Test
    public void signUpUsernameTaken() {
        // Given
        var initialRequest = signUpRequest();
        signUp(initialRequest);

        var username = initialRequest.getUsername();
        var request = signUpRequest();
        request.setUsername(username);

        // When
        var result = postForMvcResult(signUpPath, request);

        // Then
        assertMvcResultMatch(result, status().isBadRequest());
    }

    @Test
    public void signUpBadUsername() {
        // Given
        var request = signUpRequest();
        var badUsername = "";
        request.setUsername(badUsername);

        // When
        var result = postForMvcResult(signUpPath, request);

        // Then
        assertMvcResultMatch(result, status().isBadRequest());
    }

    @Test
    public void signUpBadPassword() {
        // Given
        var request = signUpRequest();
        var badPassword = "";
        request.setPassword(badPassword);

        // When
        var result = postForMvcResult(signUpPath, request);

        // Then
        assertMvcResultMatch(result, status().isBadRequest());
    }

    @SneakyThrows
    private SignUpResponse signUp(SignUpRequest request) {
        return postForResponseObject(signUpPath, request, SignUpResponse.class, status().isOk());
    }

    @SneakyThrows
    private <T> T postForResponseObject(
            String path, Object request, Class<T> responseType, ResultMatcher statusCodeMatcher) {
        var responseBytes =
                postForMvcResult(path, request)
                        .andExpect(statusCodeMatcher)
                        .andReturn()
                        .getResponse()
                        .getContentAsByteArray();

        return objectMapper().readValue(responseBytes, responseType);
    }

    @SneakyThrows
    private ResultActions postForMvcResult(String path, Object request) {
        return mockMvc()
                .perform(
                        post(path)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper().writeValueAsBytes(request)));
    }

    private void assertSignUpResponse(SignUpResponse response, SignUpRequest request) {
        assertSoftly(
                softly -> {
                    var user = response.getUser();
                    softly.assertThat(user.getId()).isNotNull();
                    softly.assertThat(user.getUsername()).isEqualTo(request.getUsername());

                    var tokens = response.getTokens();
                    softly.assertThat(tokens.getAccess()).isNotBlank();
                    softly.assertThat(tokens.getRefresh()).isNotBlank();
                });
    }

    @SneakyThrows
    private void assertMvcResultMatch(ResultActions result, ResultMatcher statusCodeMatcher) {
        result.andExpect(statusCodeMatcher);
    }
}
