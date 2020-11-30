package com.eshilov.auth.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {

    public static void throwValidationException(String message) {
        throw new IllegalArgumentException(message);
    }
}
