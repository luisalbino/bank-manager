package com.bankmanager.application.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import oshi.jna.platform.mac.SystemB;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertHelper {

    public static <T> T get(Object object, T def) {
        T result = null;

        if (Objects.nonNull(object)) {
            result = (T) object;
        }

        if (Objects.nonNull(def)) {
            result = def;
        }
        
        return result;
    }
}
