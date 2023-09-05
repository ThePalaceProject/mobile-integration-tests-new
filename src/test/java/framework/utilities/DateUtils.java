package framework.utilities;

import constants.RegEx;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateUtils {
    public static Duration getDuration(String date) {
        String formattedString = "";
        List<String> parts = Arrays.asList(date.split(":"));
        if (parts.size() > 2) {
            formattedString = "PT" + parts.get(0) + "H" + parts.get(1) + "M" + parts.get(2) + "S";
        } else if (parts.size() > 1) {
            formattedString = "PT" + parts.get(0) + "M" + parts.get(1) + "S";
        }
        return Duration.parse(formattedString);
    }

    public static LocalDateTime getExpectedLocalDateTime(String stringExpectedDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(RegEx.DATE_TIME_FORMAT);
        return LocalDateTime.parse(deleteSomeCharactersForExpectedDateTime(stringExpectedDateTime), dateTimeFormatter);
    }

    public static LocalDateTime getActualLocalDateTime(String stringActualDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(RegEx.DATE_TIME_FORMAT);
        return LocalDateTime.parse(stringActualDateTime, dateTimeFormatter);
    }

    private static String deleteSomeCharactersForExpectedDateTime(String stringExpectedDateTime) {
        return stringExpectedDateTime.split("\\+")[0].replace("T", " ");
    }
}
