package com.bankmanager.application.service.expenses;

import com.bankmanager.application.entities.cash.CashFlowEntity;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.enums.cash.FlowTypeEnum;
import com.bankmanager.application.repositories.expenses.ExpenseRepository;
import com.bankmanager.application.service.AbstractService;
import com.bankmanager.application.service.user.UserService;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class ExpenseService extends AbstractService<ExpenseEntity, ExpenseRepository> {

    private final UserService userService;
    private final CashFlowService cashFlowService;

    protected ExpenseService(ExpenseRepository repository, UserService userService, CashFlowService cashFlowService) {
        super(repository);
        this.userService = userService;
        this.cashFlowService = cashFlowService;
    }

    public void create(ExpenseEntity expense) {
        expense.setUser(userService.getLoggedUser());
        save(expense);
    }

    public void pay(ExpenseEntity expense, Double value) {
        var operationDate = LocalDateTime.now();

        var cashFlow = new CashFlowEntity();
        cashFlow.setDescription("Despesa");
        cashFlow.setFlow(FlowTypeEnum.OUTFLOW);
        cashFlow.setOperationDate(operationDate);
        cashFlow.setValue(value);
        cashFlow.setExpense(expense);
        cashFlowService.save(cashFlow);

        expense.setLastTimePaid(operationDate);
        this.save(expense);
    }

    @Override
    public Collection<ExpenseEntity> getAll() {
        return repository.getByUser(userService.getLoggedUser());
    }
}
