package com.eshilov.auth.testhelp;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import com.eshilov.auth.generated.model.LogInRequest;
import com.eshilov.auth.generated.model.RefreshTokensRequest;
import com.eshilov.auth.generated.model.SignUpRequest;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataUtils {

    private static final int PASSWORD_PART_LENGTH = 5;

    private static final Faker FAKER = Faker.instance();

    public static SignUpRequest signUpRequest() {
        return SignUpRequest.builder().username(username()).password(password()).build();
    }

    public static LogInRequest logInRequest(SignUpRequest signUpRequest) {
        return LogInRequest.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .build();
    }

    public static RefreshTokensRequest refreshRequest(String refreshToken) {
        return RefreshTokensRequest.builder().refreshToken(refreshToken).build();
    }

    public static String username() {
        return FAKER.internet().emailAddress();
    }

    public static String password() {
        return randomAlphabetic(PASSWORD_PART_LENGTH) + randomNumeric(PASSWORD_PART_LENGTH);
    }
}
