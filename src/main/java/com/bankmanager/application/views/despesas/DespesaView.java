package com.bankmanager.application.views.despesas;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.despesas.CadastroDespesaComponent;
import com.bankmanager.application.components.despesas.ConsultaDespesaComponent;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.bankmanager.application.services.expenses.TransacaoService;
import com.bankmanager.application.services.expenses.DespesaService;
import com.bankmanager.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Despesas")
@Route(value = "", layout = MainLayout.class)
public class DespesaView extends VerticalLayout {

    private final CadastroDespesaComponent cadastroDespesaComponent;
    private final ConsultaDespesaComponent consultaDespesaComponent;

    public DespesaView(DespesaService despesaService, CardTrasacaoService cardTrasacaoService) {
        consultaDespesaComponent = new ConsultaDespesaComponent(despesaService, cardTrasacaoService);
        cadastroDespesaComponent = new CadastroDespesaComponent(despesaService, consultaDespesaComponent::carregarCards);
        buildUI();
    }

    private void buildUI() {
        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(
                getButtonNewExpense(),
                HTMLHelper.getHR(),
                cadastroDespesaComponent,
                consultaDespesaComponent
        );

        add(layout);
    }

    private Component getButtonNewExpense() {
        var result = new CustomButton("Nova despesa");
        result.setIcon(VaadinIcon.PLUS.create());
        result.addClickListener(event -> cadastroDespesaComponent.open());

        return result;
    }
}
