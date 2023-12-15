package com.bankmanager.application.views.expenses;

import com.bankmanager.application.component.expenses.NewExpenseComponent;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.bankmanager.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Contas a pagar")
@Route(value = "", layout = MainLayout.class)
public class MonthlyExpensesView extends VerticalLayout {

    private final NewExpenseComponent newExpenseComponent;

    public MonthlyExpensesView(ExpenseService expenseService) {
        newExpenseComponent = new NewExpenseComponent(expenseService);

        buildUI();
    }

    private void buildUI() {
        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(
                getButtonNewExpense(),
                HTMLHelper.getHR(),
                newExpenseComponent
        );

        add(layout);
    }

    private Component getButtonNewExpense() {
        var result = new Button("Nova despesa");
        result.setIcon(VaadinIcon.PLUS.create());
        result.addClickListener(event -> newExpenseComponent.open());

        return result;
    }
}
