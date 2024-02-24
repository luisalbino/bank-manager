package com.bankmanager.application.enums.despesas.transacoes;

public enum TipoPerformanceEnum {

    POSITIVO,
    NEGATIVO,
    NEUTRO;

    public static boolean isPositivo(TipoPerformanceEnum e) {
        return POSITIVO.equals(e);
    }

    public static boolean isNegativo(TipoPerformanceEnum e) {
        return NEGATIVO.equals(e);
    }
}
