package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.despesas.carousel.CardExpenseComponent;
import com.bankmanager.application.services.expenses.CardExpenseService;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConsultaDespesaComponent extends VerticalLayout {

    private final DespesaService despesaService;
    private final CashFlowService transacaoService;
    private final CardExpenseService cardExpenseService;

    public ConsultaDespesaComponent(DespesaService despesaService, CashFlowService transacaoService, CardExpenseService cardExpenseService) {
        this.despesaService = despesaService;
        this.transacaoService = transacaoService;
        this.cardExpenseService = cardExpenseService;

        setSizeFull();
        carregarCards();
    }

    public void carregarCards() {
        this.removeAll();
        for (var expense : despesaService.getAll()) {
            this.add(new CardExpenseComponent(expense, despesaService, transacaoService, cardExpenseService, this::carregarCards));
        }
    }
}
