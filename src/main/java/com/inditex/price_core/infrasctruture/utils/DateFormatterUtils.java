package com.inditex.price_core.infrasctruture.utils;

import com.inditex.price_core.infrasctruture.adapter.controller.exception.InvalidDateFormatException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatterUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String FORMATDATE_MESSAGE = "Invalid Date Format";

    private static final String ERRORTYPEMESSAGE = "Type Mismatch";

    private static final String INTERNALSERVERERROR = "Internal Server Error";

    public static String getInternalErrorMessage(){
        return INTERNALSERVERERROR;
    }

    public static String getErrortypeMessage(){
        return ERRORTYPEMESSAGE;
    }

    public static String getFormatDateMessage(){
        return FORMATDATE_MESSAGE;
    }

    public static LocalDateTime parseApplicationDate(String applicationDate) {
        try {
            return LocalDateTime.parse(applicationDate, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(FORMATDATE_MESSAGE);
        }
    }

}
