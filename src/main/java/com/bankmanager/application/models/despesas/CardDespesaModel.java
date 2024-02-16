package com.bankmanager.application.models.despesas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDespesaModel {

    private Long idExpense;

    private String paymentDate;

    private String competencyDate;

    private Double value;

    private String valueDisplay;
}
