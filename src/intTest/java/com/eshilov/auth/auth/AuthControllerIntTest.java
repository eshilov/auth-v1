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

    @SneakyThrows
    private SignUpResponse signUp(SignUpRequest request) {
        var responseBytes =
                mockMvc()
                        .perform(
                                post(signUpPath)
                                        .contentType(APPLICATION_JSON)
                                        .content(objectMapper().writeValueAsBytes(request)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsByteArray();

        return objectMapper().readValue(responseBytes, SignUpResponse.class);
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
}
