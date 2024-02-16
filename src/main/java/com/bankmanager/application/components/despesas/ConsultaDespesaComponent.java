package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.despesas.carousel.CardExpenseComponent;
import com.bankmanager.application.services.expenses.CardExpenseService;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConsultaDespesaComponent extends VerticalLayout {

    private final DespesaService despesaService;
    private final CashFlowService cashFlowService;
    private final CardExpenseService cardExpenseService;

    public ConsultaDespesaComponent(DespesaService despesaService, CashFlowService cashFlowService, CardExpenseService cardExpenseService) {
        this.despesaService = despesaService;
        this.cashFlowService = cashFlowService;
        this.cardExpenseService = cardExpenseService;

        setSizeFull();
        refresh();
    }

    public void refresh() {
        this.removeAll();
        for (var expense : despesaService.getAll()) {
            this.add(new CardExpenseComponent(expense, despesaService, cashFlowService, cardExpenseService, this::refresh));
        }
    }
}
