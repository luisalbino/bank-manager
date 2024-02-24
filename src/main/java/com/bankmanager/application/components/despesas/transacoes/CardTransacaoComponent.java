package com.bankmanager.application.components.despesas.transacoes;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.models.despesas.CardDespesaModel;
import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardTransacaoComponent extends Div {

    private final DespesaEntity despesaEntity;

    private final CardTrasacaoService cardTrasacaoService;

    private final CadastroTransacaoComponent cadastroTransacaoComponent;

    public CardTransacaoComponent(DespesaEntity despesaEntity, DespesaService despesaService, CashFlowService cashFlowService, CardTrasacaoService cardTrasacaoService, Runnable afterPay) {
        this.despesaEntity = despesaEntity;
        this.cardTrasacaoService = cardTrasacaoService;
        this.cadastroTransacaoComponent = new CadastroTransacaoComponent(despesaService, despesaEntity, afterPay);

        setWidthFull();
        getStyle().set("padding", "5px");
        getStyle().set("box-radius", "4px");
        getStyle().set("box-shadow", "rgba(50, 50, 93, 0.25) 0px 6px 12px -2px, rgba(0, 0, 0, 0.3) 0px 3px 7px -3px");

        createCabecalho();
    }

    public void createCabecalho() {
        var layout = new VerticalLayout();
        layout.setSizeFull();

        var badge = new Span(despesaEntity.isPago() ? "Pago" : "Não Pago");
        badge.getElement().getThemeList().add("badge small " + (despesaEntity.isPago() ? "success" : "error"));

        layout.add(
                new H2(despesaEntity.getNome()),
                badge,
                HTMLHelper.getHR(),
                getExpenseResume(),
                HTMLHelper.getHR(),
                getButtonPaid()
        );

        add(layout);
    }

    private Component getExpenseResume() {
        var grid = new Grid<CardDespesaModel>();
        grid.addComponentColumn((expenseModel) -> {
            var buttonEdit = new CustomButton("Edit");
            buttonEdit.addClickListener(event -> {
            });

            return buttonEdit;
        });
        grid.addColumn(CardDespesaModel::getPaymentDate).setHeader("Pagamento");
        grid.addColumn(CardDespesaModel::getCompetencyDate).setHeader("Competência");
        grid.addColumn(CardDespesaModel::getValueDisplay).setHeader("Valor").setKey("value");

        var cardsExpenses = cardTrasacaoService.getModels(despesaEntity);
        grid.setItems(cardsExpenses);
        grid.getColumnByKey("value")
                .setFooter(cardTrasacaoService.getFooterValue(cardsExpenses));

        var layout = new VerticalLayout();
        layout.setHeight("250px");
        layout.add(grid);

        return layout;
    }

    private Component getButtonPaid() {
        var buttonPaid = new CustomButton("Pagar");
        buttonPaid.addClickListener(event -> cadastroTransacaoComponent.open());

        var layout = new HorizontalLayout();
        layout.add(buttonPaid);
        layout.setWidth("100%");
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }
}
