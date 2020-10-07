package com.sky7th.devtimeline.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter MILLISECOND_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String toStringDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(FORMATTER);
    }

    public static String toStringDate(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }

    public static LocalDateTime toLocalDateTime(String time) {
        return LocalDateTime.parse(time, FORMATTER);
    }

    public static LocalDateTime toLocalDateTimeForMilisecond(String time) {
        return LocalDateTime.parse(time, MILLISECOND_FORMATTER);
    }

    public static String toStringNowUntilMilisecond() {
        return LocalDateTime.now().format(MILLISECOND_FORMATTER);
    }
}
