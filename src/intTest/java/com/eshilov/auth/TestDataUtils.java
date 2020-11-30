package com.eshilov.auth;

import com.eshilov.auth.generated.model.SignUpRequest;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataUtils {

    private static final Faker FAKER = Faker.instance();

    public static SignUpRequest signUpRequest() {
        return SignUpRequest.builder().username(username()).password(password()).build();
    }

    public static String username() {
        return FAKER.internet().emailAddress();
    }

    public static String password() {
        return FAKER.internet().password();
    }
}
