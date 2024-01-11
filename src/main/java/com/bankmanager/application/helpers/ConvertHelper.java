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
        Double result = null;

        try {
            result = toDouble(object);
        } catch (Exception ex) {
            result = def;
        }

        return result;
    }

    public static String toString(Object object) {
        return object.toString();
    }

    public static String toString(Object object, String def) {
        String result = null;

        try {
            result = toString(object);
        } catch (Exception ex) {
            result = def;
        }

        return result;
    }
}
