package com.bankmanager.application.components.expenses.carousel;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.components.expenses.ConfirmExpensePaymentComponent;
import com.bankmanager.application.components.expenses.EditExpensePaymentComponent;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.helpers.HTMLHelper;
import com.bankmanager.application.models.expenses.carousel.CardExpenseModel;
import com.bankmanager.application.services.expenses.CardExpenseService;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.bankmanager.application.services.expenses.ExpenseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardExpenseComponent extends Div {

    private final ExpenseEntity expense;

    private final CardExpenseService cardExpenseService;

    private final ConfirmExpensePaymentComponent confirmExpensePaymentComponent;

    private final EditExpensePaymentComponent editExpensePaymentComponent;

    public CardExpenseComponent(ExpenseEntity expense, ExpenseService expenseService, CashFlowService cashFlowService, CardExpenseService cardExpenseService, Runnable afterPay) {
        this.expense = expense;
        this.cardExpenseService = cardExpenseService;
        this.confirmExpensePaymentComponent = new ConfirmExpensePaymentComponent(expenseService, expense, afterPay);
        this.editExpensePaymentComponent = new EditExpensePaymentComponent(cashFlowService, afterPay);

        setWidth("450px");
        getStyle().set("box-shadow", "rgba(50, 50, 93, 0.25) 0px 6px 12px -2px, rgba(0, 0, 0, 0.3) 0px 3px 7px -3px");
        getStyle().set("box-radius", "4px");
        getStyle().set("padding", "5px");

        buildHeader();
    }

    public void buildHeader() {
        var layout = new VerticalLayout();
        layout.setSizeFull();

        var badge = new Span(expense.isPaid() ? "Pago" : "Não Pago");
        badge.getElement().getThemeList().add("badge small " + (expense.isPaid() ? "success" : "error"));

        layout.add(
                new H2(expense.getName()),
                badge,
                HTMLHelper.getHR(),
                getExpenseResume(),
                HTMLHelper.getHR(),
                getButtonPaid()
        );

        add(layout);
    }

    private Component getExpenseResume() {
        var grid = new Grid<CardExpenseModel>();
        grid.addComponentColumn((expenseModel) -> {
            var buttonEdit = new CustomButton("Edit");
            buttonEdit.addClickListener(event -> {
                this.editExpensePaymentComponent.open(expenseModel.getIdExpense());
            });

            return buttonEdit;
        });
        grid.addColumn(CardExpenseModel::getPaymentDate).setHeader("Pagamento");
        grid.addColumn(CardExpenseModel::getCompetencyDate).setHeader("Competência");
        grid.addColumn(CardExpenseModel::getValueDisplay).setHeader("Valor").setKey("value");

        var cardsExpenses = cardExpenseService.getModels(expense);
        grid.setItems(cardsExpenses);
        grid.getColumnByKey("value")
                .setFooter(cardExpenseService.getFooterValue(cardsExpenses));

        var layout = new VerticalLayout();
        layout.setHeight("250px");
        layout.add(grid);

        return layout;
    }

    private Component getButtonPaid() {
        var buttonPaid = new CustomButton("Pagar");
        buttonPaid.addClickListener(event -> confirmExpensePaymentComponent.open());

        var layout = new HorizontalLayout();
        layout.add(buttonPaid);
        layout.setWidth("100%");
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }
}
