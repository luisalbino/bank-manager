package com.bankmanager.application.components.expenses;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ConfirmExpensePaymentComponent extends CustomDialog {

    private final ExpenseEntity expense;
    private final Binder<ValueModel> binder = new Binder<>();

    public ConfirmExpensePaymentComponent(ExpenseService expenseService, ExpenseEntity expense, Runnable afterPay) {
        super("Pagar despesa");

        this.expense = expense;

        var fieldValue = new NumberField("Valor pago");
        binder.forField(fieldValue)
                .withValidator(value -> ConvertHelper.toDouble(value, 0D) > 0, "O valor precisa ser maior que zero (0)!")
                .bind(ValueModel::getValue, ValueModel::setValue);

        add(fieldValue);

        var buttonConfirm = new CustomButton("Confirmar!");
        buttonConfirm.addClickListener(event -> {
            if (binder.isValid()) {
                expenseService.pay(expense, binder.getBean().getValue());
                afterPay.run();
                close();
                NotificationHelper.success("Despesa paga com sucesso!");
            }
        });
        getFooter().add(buttonConfirm);
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(new ValueModel(ConvertHelper.toDouble(expense.getValue(), 0D)), binder);
        super.open();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ValueModel {
        private Double value;
    }
}
