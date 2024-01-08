package com.bankmanager.application.views.expenses;

import com.bankmanager.application.component.expenses.NewExpenseComponent;
import com.bankmanager.application.component.expenses.carousel.CarouselExpenseComponent;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.service.expenses.CardExpenseService;
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
    private final CarouselExpenseComponent carouselExpenseComponent;

    public MonthlyExpensesView(ExpenseService expenseService, CardExpenseService cardExpenseService) {
        newExpenseComponent = new NewExpenseComponent(expenseService);
        carouselExpenseComponent = new CarouselExpenseComponent(expenseService, cardExpenseService);

        buildUI();
    }

    private void buildUI() {
        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(
                getButtonNewExpense(),
                HTMLHelper.getHR(),
                newExpenseComponent,
                carouselExpenseComponent
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
