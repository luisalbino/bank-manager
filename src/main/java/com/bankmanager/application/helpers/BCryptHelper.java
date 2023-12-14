package com.bankmanager.application.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

@NoArgsConstructor(access = AccessLevel.NONE)
public class BCryptHelper {

    public static String toHash(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt(12));
    }
}
