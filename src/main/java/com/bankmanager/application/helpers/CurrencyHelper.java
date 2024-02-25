package com.bankmanager.application.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyHelper {

    private final static NumberFormat NF = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String getMoney(Double value) {
        return NF.format(value);
    }

    public static String getPercentual(Double valor) {
        var bigDecimal = BigDecimal.valueOf(ConvertHelper.toDouble(valor, 0D)).setScale(2, RoundingMode.HALF_EVEN);
        return ConvertHelper.toString(bigDecimal.doubleValue(), "0") + "%";
    }
}
