package com.bankmanager.application.components.despesas;

import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.bankmanager.application.services.expenses.TransacaoService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConsultaDespesaComponent extends VerticalLayout {

    private final DespesaService despesaService;
    private final TransacaoService transacaoService;
    private final CardTrasacaoService cardTrasacaoService;

    public ConsultaDespesaComponent(DespesaService despesaService, TransacaoService transacaoService, CardTrasacaoService cardTrasacaoService) {
        this.despesaService = despesaService;
        this.transacaoService = transacaoService;
        this.cardTrasacaoService = cardTrasacaoService;

        setSizeFull();
        carregarCards();
    }

    public void carregarCards() {
        this.removeAll();
        for (var despesa : despesaService.getAll()) {
            this.add(new CardDespesaComponent(despesa, despesaService, cardTrasacaoService, this::carregarCards));
        }
    }
}
