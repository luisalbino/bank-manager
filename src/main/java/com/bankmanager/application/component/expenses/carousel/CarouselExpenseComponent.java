package com.bankmanager.application.component.expenses.carousel;

import com.bankmanager.application.service.expenses.CardExenseService;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CarouselExpenseComponent extends HorizontalLayout {

    private final ExpenseService expenseService;
    private final CardExenseService cardExenseService;

    public CarouselExpenseComponent(ExpenseService expenseService, CardExenseService cardExenseService) {
        this.expenseService = expenseService;
        this.cardExenseService = cardExenseService;
        refresh();
    }

    private void refresh() {
        removeAll();
        var expenses = expenseService.getAll();
        for (var expense : expenses) {
            add(new CardExpenseComponent(expense, expenseService, cardExenseService));
        }
    }
}
