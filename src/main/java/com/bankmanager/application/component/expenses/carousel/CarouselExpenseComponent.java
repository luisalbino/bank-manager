package com.bankmanager.application.component.expenses.carousel;

import com.bankmanager.application.service.expenses.CardExpenseService;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CarouselExpenseComponent extends HorizontalLayout {

    private final ExpenseService expenseService;
    private final CardExpenseService cardExpenseService;

    public CarouselExpenseComponent(ExpenseService expenseService, CardExpenseService cardExpenseService) {
        this.expenseService = expenseService;
        this.cardExpenseService = cardExpenseService;
        refresh();
    }

    private void refresh() {
        removeAll();
        var expenses = expenseService.getAll();
        for (var expense : expenses) {
            add(new CardExpenseComponent(expense, expenseService, cardExpenseService));
        }
    }
}
