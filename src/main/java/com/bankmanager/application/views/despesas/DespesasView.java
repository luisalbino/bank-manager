package com.bankmanager.application.views.despesas;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.despesas.NovaDespesaComponent;
import com.bankmanager.application.components.despesas.ConsultaDespesaComponent;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.services.expenses.CardExpenseService;
import com.bankmanager.application.services.expenses.CashFlowService;
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
public class DespesasView extends VerticalLayout {

    private final NovaDespesaComponent novaDespesaComponent;
    private final ConsultaDespesaComponent consultaDespesaComponent;

    public DespesasView(DespesaService despesaService, CashFlowService cashFlowService, CardExpenseService cardExpenseService) {
        consultaDespesaComponent = new ConsultaDespesaComponent(despesaService, cashFlowService, cardExpenseService);
        novaDespesaComponent = new NovaDespesaComponent(despesaService, consultaDespesaComponent::refresh);
        buildUI();
    }

    private void buildUI() {
        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(
                getButtonNewExpense(),
                HTMLHelper.getHR(),
                novaDespesaComponent,
                consultaDespesaComponent
        );

        add(layout);
    }

    private Component getButtonNewExpense() {
        var result = new CustomButton("Nova despesa");
        result.setIcon(VaadinIcon.PLUS.create());
        result.addClickListener(event -> novaDespesaComponent.open());

        return result;
    }
}
