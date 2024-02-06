package com.bankmanager.application.components.expenses.carousel;

import com.bankmanager.application.services.expenses.CardExpenseService;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.bankmanager.application.services.expenses.ExpenseService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CarouselExpenseComponent extends HorizontalLayout {

    private final ExpenseService expenseService;
    private final CashFlowService cashFlowService;
    private final CardExpenseService cardExpenseService;

    public CarouselExpenseComponent(ExpenseService expenseService, CashFlowService cashFlowService, CardExpenseService cardExpenseService) {
        this.expenseService = expenseService;
        this.cashFlowService = cashFlowService;
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
            this.add(new CardExpenseComponent(expense, expenseService, cashFlowService, cardExpenseService, this::refresh));
        }
    }
}
