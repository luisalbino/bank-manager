package com.bankmanager.application.enums.despesas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDespesaEnum {

    RECORRENTE("Recorrente"),
    MENSAL("Mensal"),
    ANUAL("Anual");

    String descricao;
}
