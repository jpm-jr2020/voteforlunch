package com.herokuapp.voteforlunch.web.converter;

import com.herokuapp.voteforlunch.util.DateTimeUtil;
import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.parseLocalDate;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.parseLocalDateTime;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeUtil.DATE_FORMATTER);
        }
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

        @Override
        public LocalDateTime parse(String text, Locale locale) {
            return parseLocalDateTime(text);
        }

        @Override
        public String print(LocalDateTime lt, Locale locale) {
            return lt.format(DateTimeUtil.DATE_TIME_FORMATTER);
        }
    }
}
