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
        var result = toDouble(object);

        if (Objects.isNull(result)) {
            result = def;
        }

        return result;
    }
}
