package com.bankmanager.application.component.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class NewExpenseComponent extends Dialog {

    private final Runnable afterCreating;
    private final Binder<ExpenseEntity> binder = new Binder<>();

    public NewExpenseComponent(ExpenseService expenseService, Runnable afterCreating) {
        this.afterCreating = afterCreating;

        setHeaderTitle("Nova despensa");
        getHeader().add(HTMLHelper.getHR());

        var fieldName = new TextField("Nome");
        binder.forField(fieldName)
                .withValidator(StringUtils::isNotBlank, "Informe um nome!")
                .bind(ExpenseEntity::getName, ExpenseEntity::setName);

        var fieldValue = new NumberField("Valor");
        binder.forField(fieldValue)
                .withValidator(Objects::nonNull, "Informe um valor a ser pago!")
                .bind(ExpenseEntity::getValue, ExpenseEntity::setValue);

        var fieldExpireDate = new IntegerField("Dia de expiração");
        binder.forField(fieldExpireDate)
                .withValidator(Objects::nonNull, "Informe o dia da expiração!")
                .bind(ExpenseEntity::getExpireDay, ExpenseEntity::setExpireDay);

        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(fieldName, fieldValue, fieldExpireDate);

        add(layout);

        var buttonCancel = new Button("Cancelar");
        buttonCancel.addClickListener(event -> close());

        var buttonSave = new Button("Salvar");
        buttonSave.addClickListener(event -> {
            binder.validate();
            if (binder.isValid()) {
                expenseService.create(binder.getBean());
                afterCreating.run();
                close();
            }
        });

        getFooter().add(buttonCancel, buttonSave);
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(new ExpenseEntity(), binder);
        super.open();
    }
}
