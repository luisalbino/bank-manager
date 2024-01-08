package com.bankmanager.application.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeHelper {

    public static String getMonthStr(LocalDateTime localDateTime) {
        return Objects.isNull(localDateTime) ? "" : localDateTime.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getDateStr(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
