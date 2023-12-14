package com.bankmanager.application.views.bills;

import com.bankmanager.application.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Contas a pagar")
@Route(value = "", layout = MainLayout.class)
public class BillsToPayView extends VerticalLayout {
}
