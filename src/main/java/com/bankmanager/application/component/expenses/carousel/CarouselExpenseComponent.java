package com.bankmanager.application.component.expenses.carousel;

import com.bankmanager.application.service.expenses.CardExpenseService;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CarouselExpenseComponent extends HorizontalLayout {

    private final ExpenseService expenseService;
    private final CardExpenseService cardExpenseService;

    public CarouselExpenseComponent(ExpenseService expenseService, CardExpenseService cardExpenseService) {
        this.expenseService = expenseService;
        this.cardExpenseService = cardExpenseService;

        this.getStyle().set("margin", "10px 2px 10px 2px");
        this.getStyle().set("display", "flex");
        this.getStyle().set("flex-wrap", "wrap");
        this.getStyle().set("justify-content", "space-between");
        setSizeFull();

        refresh();
    }

    public void refresh() {
        this.removeAll();
        for (var expense : expenseService.getAll()) {
            this.add(new CardExpenseComponent(expense, expenseService, cardExpenseService, this::refresh));
        }
    }
}
