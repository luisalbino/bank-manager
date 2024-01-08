package com.bankmanager.application.component.expenses.carousel;

import com.bankmanager.application.service.expenses.ExpenseService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CarouselExpenseComponent extends HorizontalLayout {

    private final ExpenseService expenseService;

    public CarouselExpenseComponent(ExpenseService expenseService) {
        this.expenseService = expenseService;
        refresh();
    }

    private void refresh() {
        removeAll();
        var expenses = expenseService.getAll();
        for (var expense : expenses) {
            add(new CardExpenseComponent(expense, expenseService));
        }
    }
}
