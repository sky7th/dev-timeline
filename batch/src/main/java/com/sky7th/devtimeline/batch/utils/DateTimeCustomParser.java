package com.sky7th.devtimeline.batch.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeCustomParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseClosingTime(String closingDate) {
        closingDate = removeKorean(closingDate);
        String resultDate = "";
        String[] dates;
        if (closingDate.equals("")) {
            return null;

        } else if (closingDate.contains("~")) {
            String startDate = closingDate.split("~")[0];
            dates = startDate.split("[.]");

        } else {
            dates = closingDate.split(" ");
        }

        resultDate = dates[0] + "-" + get2Digit(dates[1]) + "-" + get2Digit(dates[2]) + " 00:00:00";
        return LocalDateTime.parse(removeKorean(resultDate), FORMATTER);
    }

    private static String removeKorean(String date) {
        String result = date.replaceAll("[ㄱ-ㅎㅏ-ㅣ가-힣]", "");
        result = result.trim();
        return result;
    }

    private static String get2Digit(String date) {
        if (date.length()==1)
            return "0"+date;
        else
            return date;
    }

    public static LocalDateTime parseDateTime(String date) {
        String resultDate = "";

        if (date.equals("")) {
            return LocalDateTime.now();

        } else {
            String[] dates = date.split("[.]");
            resultDate = dates[0] + "-" + get2Digit(dates[1]) + "-" + get2Digit(dates[2]) + " 00:00:00";
        }
        return LocalDateTime.parse(removeKorean(resultDate), FORMATTER);
    }
}
