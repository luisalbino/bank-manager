package com.bankmanager.application.models.expenses.carousel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardExpenseModel {

    private String paymentDate;

    private String competencyDate;

    private Double value;

    private String valueDisplay;
}
