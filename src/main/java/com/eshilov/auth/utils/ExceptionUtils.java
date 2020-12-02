package com.eshilov.auth.utils;

import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {

    public static void throwValidationException(String message) {
        throw validationExceptionSupplier(message).get();
    }

    public static Supplier<RuntimeException> validationExceptionSupplier(String message) {
        return () -> new IllegalArgumentException(message);
    }
}
