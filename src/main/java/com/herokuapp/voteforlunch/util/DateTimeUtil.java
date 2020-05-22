package com.herokuapp.voteforlunch.util;

import com.herokuapp.voteforlunch.util.exception.TimeViolationException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    public static final LocalDate YESTERDAY = LocalDate.now().minus(1, ChronoUnit.DAYS);
    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate TOMORROW = LocalDate.now().plus(1, ChronoUnit.DAYS);

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    private static LocalTime NO_REVOTE_TIME = LocalTime.of(11, 0);

    public static LocalDate nullDateToMin(LocalDate date) {
        return date != null ? date : MIN_DATE;
    }

    public static LocalDate nullDateToMax(LocalDate date) {
        return date != null ? date : MAX_DATE;
    }

    public static LocalDate dateOrMinDate(LocalDate date) {
        return date != null ? date : MIN_DATE;
    }

    public static LocalDate dateOrMaxDate(LocalDate date) {
        return date != null ? date : MAX_DATE;
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str, DateTimeUtil.DATE_FORMATTER);
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str, DateTimeUtil.TIME_FORMATTER);
    }

    public static @Nullable LocalDateTime parseLocalDateTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDateTime.parse(str, DateTimeUtil.DATE_TIME_FORMATTER);
    }

    public static void checkCanRevote(LocalTime time) {
        if (NO_REVOTE_TIME.isBefore(time)) {
            throw new TimeViolationException(time.toString());
        }
    }

    public static void setNoRevoteTime(LocalTime localTime) {
        NO_REVOTE_TIME = localTime;
    }
}
