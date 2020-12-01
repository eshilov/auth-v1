package com.eshilov.auth;

import static org.apache.commons.lang3.RandomStringUtils.*;

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

    public static String username() {
        return FAKER.internet().emailAddress();
    }

    public static String password() {
        return randomAlphabetic(PASSWORD_PART_LENGTH) + randomNumeric(PASSWORD_PART_LENGTH);
    }
}
