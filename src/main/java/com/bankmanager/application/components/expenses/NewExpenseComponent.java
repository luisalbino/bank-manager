package com.bankmanager.application.components.expenses;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class NewExpenseComponent extends CustomDialog {

    private final Binder<ExpenseEntity> binder = new Binder<>();

    public NewExpenseComponent(ExpenseService expenseService, Runnable afterCreating) {
        super("Nova despesa");

        addConfirmAction(() -> {
            binder.validate();
            if (binder.isValid()) {
                expenseService.create(binder.getBean());
                afterCreating.run();
                NotificationHelper.success("Despesa criada com sucesso!");
                close();
            }
        });

        var fieldName = new TextField("Nome");
        binder.forField(fieldName)
                .withValidator(StringUtils::isNotBlank, "Informe um nome!")
                .bind(ExpenseEntity::getName, ExpenseEntity::setName);

        var fieldValue = new NumberField("Valor (Sugestão)");
        binder.forField(fieldValue)
                .withValidator(Objects::nonNull, "Informe uma sugestão de valor a ser pago!")
                .bind(ExpenseEntity::getValue, ExpenseEntity::setValue);

        var fieldExpireDate = new IntegerField("Dia de vencimento");
        binder.forField(fieldExpireDate)
                .withValidator(Objects::nonNull, "Informe o dia do vencimento!")
                .bind(ExpenseEntity::getExpireDay, ExpenseEntity::setExpireDay);

        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(fieldName, fieldValue, fieldExpireDate);

        add(layout);
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(new ExpenseEntity(), binder);
        super.open();
    }
}
