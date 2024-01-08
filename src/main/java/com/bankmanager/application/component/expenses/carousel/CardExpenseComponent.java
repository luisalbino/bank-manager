package com.bankmanager.application.component.expenses.carousel;

import com.bankmanager.application.component.expenses.ConfirmExpensePaymentComponent;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.models.expenses.carousel.CardExpenseModel;
import com.bankmanager.application.service.expenses.CardExpenseService;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardExpenseComponent extends Div {

    private final ExpenseEntity expense;

    private final ExpenseService expenseService;
    private final CardExpenseService cardExpenseService;

    private final ConfirmExpensePaymentComponent confirmExpensePaymentComponent;

    public CardExpenseComponent(ExpenseEntity expense, ExpenseService expenseService, CardExpenseService cardExpenseService) {
        this.expense = expense;
        this.expenseService = expenseService;
        this.cardExpenseService = cardExpenseService;
        this.confirmExpensePaymentComponent = new ConfirmExpensePaymentComponent(expenseService, expense);

        setWidth("20vw");
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
                getExpenseResume(),
                HTMLHelper.getHR(),
                getButtonPaid()
        );

        add(layout);
    }

    private Component getExpenseResume() {
        var grid = new Grid<CardExpenseModel>();
        grid.addColumn(CardExpenseModel::getDate).setHeader("Data");
        grid.addColumn(CardExpenseModel::getValue).setHeader("Valor");
        grid.setItems(cardExpenseService.getModels(expense));

        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(grid);

        return layout;
    }

    private Component getButtonPaid() {
        var buttonPaid = new Button("Pagar");
        buttonPaid.getStyle().set("cursor", "pointer");
        buttonPaid.addClickListener(event -> confirmExpensePaymentComponent.open());

        var layout = new HorizontalLayout();
        layout.add(buttonPaid);
        layout.setWidth("100%");
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }
}
