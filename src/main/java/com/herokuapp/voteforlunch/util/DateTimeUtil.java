package com.herokuapp.voteforlunch.util;

import com.herokuapp.voteforlunch.util.exception.IllegalRequestDataException;
import com.herokuapp.voteforlunch.util.exception.TimeViolationException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeoutException;

public class DateTimeUtil {
    public static final LocalDate YESTERDAY = LocalDate.of(2020, 4, 29);
    public static final LocalDate TODAY = LocalDate.of(2020, 4, 30);
    public static final LocalDate TOMORROW = LocalDate.of(2020, 5, 1);

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    private static final LocalTime NO_REVOTE_TIME = LocalTime.of(11, 0);

    public static LocalDate nullDateToMin(LocalDate date) {
        return date != null ? date : MIN_DATE;
    }

    public static LocalDate nullDateToMax(LocalDate date) {
        return date != null ? date : MAX_DATE;
    }

    public static LocalDateTime dateToStartOfDay(LocalDate date) {
        return date != null ? date.atStartOfDay() : MIN_DATE.atStartOfDay();
    }

    public static LocalDateTime dateToStartOfNextDay(LocalDate date) {
        return date != null ? date.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE.atStartOfDay();
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static void checkCanRevote(LocalTime time) {
        if (NO_REVOTE_TIME.isBefore(time)) {
            throw new TimeViolationException(time.toString());
        }
    }
}
