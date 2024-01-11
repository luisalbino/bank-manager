package com.bankmanager.application.models.expenses.carousel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardExpenseModel {

    private String date;

    private Double value;

    private String valueDisplay;
}
