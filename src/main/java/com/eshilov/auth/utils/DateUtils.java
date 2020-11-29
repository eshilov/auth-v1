package com.eshilov.auth.utils;

import static java.time.ZoneOffset.UTC;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(UTC));
    }
}
