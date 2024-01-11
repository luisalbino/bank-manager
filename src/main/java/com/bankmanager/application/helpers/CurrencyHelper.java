package com.bankmanager.application.helpers;

public class CurrencyHelper {

    public static String convert(Double value) {
        return "R$ " + ConvertHelper.toString(value, "0.00");
    }
}
