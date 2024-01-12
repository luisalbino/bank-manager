package com.bankmanager.application.views.expenses;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.expenses.NewExpenseComponent;
import com.bankmanager.application.components.expenses.carousel.CarouselExpenseComponent;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.service.expenses.CardExpenseService;
import com.bankmanager.application.service.expenses.ExpenseService;
import com.bankmanager.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Despesas mensais")
@Route(value = "", layout = MainLayout.class)
public class MonthlyExpensesView extends VerticalLayout {

    private final NewExpenseComponent newExpenseComponent;
    private final CarouselExpenseComponent carouselExpenseComponent;

    public MonthlyExpensesView(ExpenseService expenseService, CardExpenseService cardExpenseService) {
        carouselExpenseComponent = new CarouselExpenseComponent(expenseService, cardExpenseService);
        newExpenseComponent = new NewExpenseComponent(expenseService, carouselExpenseComponent::refresh);
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
        var result = new CustomButton("Nova despesa");
        result.setIcon(VaadinIcon.PLUS.create());
        result.addClickListener(event -> newExpenseComponent.open());

        return result;
    }
}
