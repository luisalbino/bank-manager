package com.bankmanager.application.component.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Objects;

public class NewExpenseComponent extends Dialog {

    private final Binder<ExpenseEntity> binder = new Binder<>();

    public NewExpenseComponent() {
        binder.setBean(new ExpenseEntity());

        setHeaderTitle("Nova despensa");
        getHeader().add(HTMLHelper.getHR());

        var fieldName = new TextField("Nome");
        binder.forField(fieldName)
                .withValidator(Objects::nonNull, "Nome precisa ser informado!")
                .bind(ExpenseEntity::getName, ExpenseEntity::setName);

        var fieldValue = new NumberField("Valor");
        binder.forField(fieldValue)
                .withValidator(Objects::nonNull, "Valor da despesa precisa ser informado!")
                .bind(ExpenseEntity::getValue, ExpenseEntity::setValue);

        var fieldExpireDate = new DatePicker("Data de expiração");
        binder.forField(fieldExpireDate)
                .withValidator(Objects::nonNull, "Data de expiração precisa ser selecionada!")
                .bind(ExpenseEntity::getExpireDate, ExpenseEntity::setExpireDate);

        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(fieldName, fieldValue, fieldExpireDate);

        add(layout);

        var buttonCancel = new Button("Cancelar");
        buttonCancel.addClickListener(event -> close());

        var buttonSave = new Button("Salvar");
        buttonSave.addClickListener(event -> {

        });

        getFooter().add(buttonCancel, buttonSave);
    }

    @Override
    public void close() {
        binder.getFields().forEach(HasValue::clear);
        super.close();
    }

    @Override
    public void open() {
        super.open();
    }
}
