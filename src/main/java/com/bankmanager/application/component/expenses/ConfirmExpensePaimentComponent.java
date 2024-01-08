package com.bankmanager.application.component.expenses;

import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

public class ConfirmExpensePaimentComponent extends Dialog {

    private final ExpenseService expenseService;
    private final Binder<Double> binder = new Binder<>();

    public ConfirmExpensePaimentComponent(ExpenseService expenseService) {
        this.expenseService = expenseService;

        var fieldValue = new NumberField("Valor pago");
        binder.forField(fieldValue)
                .withValidator(value -> {


                    return false;
                }, "");
    }

    public void open(Double value) {
        BinderHelper.setAndClearFields(value, binder);
        super.open();
    }

    @Override
    public void open() {
        throw new IllegalArgumentException("Cannot open without passing a value!");
    }
}
