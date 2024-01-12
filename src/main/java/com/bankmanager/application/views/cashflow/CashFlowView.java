package com.bankmanager.application.views.cashflow;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.PermitAll;
import com.bankmanager.application.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@PermitAll
@PageTitle("Fluxo de caixa")
@Route(value = "cash-flow", layout = MainLayout.class)
public class CashFlowView extends VerticalLayout {


}
