package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.despesas.transacoes.CadastroTransacaoComponent;
import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.models.despesas.transacoes.TrasacaoModel;
import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;

public class CardDespesaComponent extends Div {

    private final DespesaEntity despesa;
    private final CardTrasacaoService cardTrasacaoService;
    private final CadastroTransacaoComponent cadastroTransacaoComponent;

    public CardDespesaComponent(DespesaEntity despesa, CardTrasacaoService cardTrasacaoService, Runnable afterPagamentoFunc) {
        this.despesa = despesa;
        this.cardTrasacaoService = cardTrasacaoService;
        this.cadastroTransacaoComponent = new CadastroTransacaoComponent(cardTrasacaoService, despesa, afterPagamentoFunc);

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
        badge.setVisible(despesa.isNotRecorrente());
        badge.getElement().getThemeList().add("badge small " + (despesa.isPago() ? "success" : "error"));

        layout.add(
                new H2(despesa.getNome() + " - " + despesa.getTipo().getDescricao() ),
                badge,
                HTMLHelper.getHR(),
                getExpenseResume(),
                HTMLHelper.getHR(),
                getButtonPaid()
        );

        add(layout);
    }

    private Component getColunaAcoes(TrasacaoModel trasacaoModel) {
        var botaoEditar = new CustomButton(VaadinIcon.PENCIL.create());
        botaoEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        botaoEditar.addClickListener(event -> {
            cadastroTransacaoComponent.editar(trasacaoModel);
        });

        return botaoEditar;
    }

    private Component getColunaPerformance(TrasacaoModel trasacaoModel) {
        var layout = new HorizontalLayout();
        layout.add(trasacaoModel.getIconePerformance());
        layout.add(new Span(trasacaoModel.getPerformance()));

        Tooltip.forComponent(layout)
            .withText(trasacaoModel.getTooltipPerformance())
            .withPosition(Tooltip.TooltipPosition.END_BOTTOM);

        return layout;
    }

    private Component getExpenseResume() {
        var grid = new Grid<TrasacaoModel>();
        grid.addComponentColumn(this::getColunaAcoes).setHeader("Ações").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(TrasacaoModel::getDataPagamentoStr).setHeader("Data Pagamento");
        grid.addColumn(TrasacaoModel::getDataCompetenciaStr).setHeader("Data Competência").setVisible(this.despesa.isNotRecorrente());
        grid.addColumn(TrasacaoModel::getValorStr).setHeader("Valor").setKey("valor");
        grid.addComponentColumn(this::getColunaPerformance).setHeader("Performance").setKey("performance");

        var cardsExpenses = cardTrasacaoService.getModels(despesa);
        grid.setItems(cardsExpenses);
        grid.getColumnByKey("valor")
                .setFooter(cardTrasacaoService.getFooterValue(cardsExpenses));

        var layout = new VerticalLayout();
        layout.setHeight("250px");
        layout.add(grid);

        return layout;
    }

    private Component getButtonPaid() {
        var buttonPaid = new CustomButton("Pagar");
        buttonPaid.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPaid.addClickListener(event -> cadastroTransacaoComponent.novo());

        var layout = new HorizontalLayout();
        layout.add(buttonPaid);
        layout.setWidth("100%");
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }
}
