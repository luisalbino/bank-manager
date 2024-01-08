package com.bankmanager.application.component.expenses.carousel;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardExpenseComponent extends Div {

    private final ExpenseEntity expense;

    private final ExpenseService expenseService;

    public CardExpenseComponent(ExpenseEntity expense, ExpenseService expenseService) {
        this.expense = expense;
        this.expenseService = expenseService;

        setWidth("350px");
        setHeight("350px");
        getStyle().set("box-shadow", "rgba(50, 50, 93, 0.25) 0px 6px 12px -2px, rgba(0, 0, 0, 0.3) 0px 3px 7px -3px");
        getStyle().set("box-radius", "4px");
        getStyle().set("padding", "5px");

        buildHeader();
    }

    public void buildHeader() {
        var layout = new VerticalLayout();
        layout.setSizeFull();

        var badge = new Span(expense.isPaid() ? "Pago" : "Não Pago");
        badge.getElement().getThemeList().add("badge small " + (expense.isPaid() ? "success" : "error"));

        layout.add(
                new H2(expense.getName()),
                badge,
                HTMLHelper.getHR(),
                HTMLHelper.getHR(),
                getButtonPaid()
        );

        add(layout);
    }

    private Component getButtonPaid() {
        var buttonPaid = new Button("Pagar");
        buttonPaid.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonPaid.addClickListener(event -> {
            System.out.println("Alguma coisa!");
        });


        var layout = new HorizontalLayout();
        layout.add(buttonPaid);
        layout.setSizeFull();
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }
}
