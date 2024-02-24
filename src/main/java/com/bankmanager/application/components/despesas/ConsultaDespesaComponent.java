package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.despesas.transacoes.CardTransacaoComponent;
import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConsultaDespesaComponent extends VerticalLayout {

    private final DespesaService despesaService;
    private final CashFlowService transacaoService;
    private final CardTrasacaoService cardTrasacaoService;

    public ConsultaDespesaComponent(DespesaService despesaService, CashFlowService transacaoService, CardTrasacaoService cardTrasacaoService) {
        this.despesaService = despesaService;
        this.transacaoService = transacaoService;
        this.cardTrasacaoService = cardTrasacaoService;

        setSizeFull();
        carregarCards();
    }

    public void carregarCards() {
        this.removeAll();
        for (var expense : despesaService.getAll()) {
            this.add(new CardTransacaoComponent(expense, despesaService, transacaoService, cardTrasacaoService, this::carregarCards));
        }
    }
}
