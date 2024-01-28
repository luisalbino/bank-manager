package com.bankmanager.application.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertHelper {

    public static Double toDouble(Object object) {
        return (Double) object;
    }

    public static Double toDouble(Object object, Double def) {
        Double result = def;

        if (Objects.nonNull(object)) {
            result = toDouble(object);
        }

        return result;
    }

    public static String toString(Object object) {
        return object.toString();
    }

    public static String toString(Object object, String def) {
        String result = def;

        if (Objects.nonNull(object)) {
            result = toString(object);
        }

        return result;
    }
}
