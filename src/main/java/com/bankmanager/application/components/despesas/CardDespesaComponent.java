package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.despesas.transacoes.CadastroTransacaoComponent;
import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.models.despesas.CardDespesaModel;
import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.bankmanager.application.services.expenses.TransacaoService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardDespesaComponent extends Div {

    private final DespesaEntity despesa;
    private final CardTrasacaoService cardTrasacaoService;
    private final CadastroTransacaoComponent cadastroTransacaoComponent;

    public CardDespesaComponent(DespesaEntity despesa, DespesaService service, CardTrasacaoService cardTrasacaoService, Runnable afterPagamentoFunc) {
        this.despesa = despesa;
        this.cardTrasacaoService = cardTrasacaoService;
        this.cadastroTransacaoComponent = new CadastroTransacaoComponent(service, despesa, afterPagamentoFunc);

        setWidthFull();
        getStyle().set("padding", "5px");
        getStyle().set("box-radius", "4px");
        getStyle().set("box-shadow", "rgba(50, 50, 93, 0.25) 0px 6px 12px -2px, rgba(0, 0, 0, 0.3) 0px 3px 7px -3px");

        createCabecalho();
    }

    public void createCabecalho() {
        var layout = new VerticalLayout();
        layout.setSizeFull();

        var badge = new Span(despesa.isPago() ? "Pago" : "Não Pago");
        badge.getElement().getThemeList().add("badge small " + (despesa.isPago() ? "success" : "error"));

        layout.add(
                new H2(despesa.getNome()),
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

        var cardsExpenses = cardTrasacaoService.getModels(despesa);
        grid.setItems(cardsExpenses);
        grid.getColumnByKey("value")
                .setFooter(cardTrasacaoService.getFooterValue(cardsExpenses));

        var layout = new VerticalLayout();
        layout.setHeight("250px");
        layout.add(grid);

        return layout;
    }

    private Component getButtonPaid() {
        var botaoEditar = new CustomButton("Editar");

        var buttonPaid = new CustomButton("Pagar");
        buttonPaid.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPaid.addClickListener(event -> cadastroTransacaoComponent.open());

        var layout = new HorizontalLayout();
        layout.add(botaoEditar, buttonPaid);
        layout.setWidth("100%");
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }
}
