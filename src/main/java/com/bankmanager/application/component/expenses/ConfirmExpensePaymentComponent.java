package com.bankmanager.application.component.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

public class ConfirmExpensePaymentComponent extends Dialog {

    private final ExpenseEntity expense;
    private final ExpenseService expenseService;
    private final Binder<Double> binder = new Binder<>();

    public ConfirmExpensePaymentComponent(ExpenseService expenseService, ExpenseEntity expense, Runnable afterPay) {
        this.expense = expense;
        this.expenseService = expenseService;

        var fieldValue = new NumberField("Valor pago");
        binder.forField(fieldValue)
                .withValidator(value -> ConvertHelper.toDouble(value, 0D) > 0, "O valor precisa ser maior que zero (0)!")
                .bind(BinderHelper::valueProviderPrimitiveType, BinderHelper::setterProviderPrimitiveType);

        add(fieldValue);

        var buttonConfirm = new Button("Confirmar!");
        buttonConfirm.addClickListener(event -> {
            if (binder.isValid()) {
                expenseService.pay(expense, binder.getBean());
                afterPay.run();
                close();
            }
        });
        getFooter().add(buttonConfirm);
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(ConvertHelper.toDouble(expense.getValue(), 0D), binder);
        super.open();
    }
}
