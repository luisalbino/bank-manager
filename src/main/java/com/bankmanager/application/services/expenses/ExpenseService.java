package com.bankmanager.application.services.expenses;

import com.bankmanager.application.entities.cash.CashFlowEntity;
import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.enums.cash.FlowTypeEnum;
import com.bankmanager.application.repositories.expenses.ExpenseRepository;
import com.bankmanager.application.services.AbstractService;
import com.bankmanager.application.services.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;

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

    public void pay(ExpenseEntity expense, LocalDate time, Double value) {
        var paidTime = time.atStartOfDay();

        var cashFlow = new CashFlowEntity();
        cashFlow.setDescription("Despesa " + expense.getName());
        cashFlow.setFlow(FlowTypeEnum.OUTFLOW);
        cashFlow.setOperationDate(paidTime);
        cashFlow.setValue(value);
        cashFlow.setExpense(expense);
        cashFlowService.save(cashFlow);

        if (paidTime.getMonth().equals(LocalDateTime.now().getMonth())) {
            expense.setLastTimePaid(paidTime);
        }

        this.save(expense);
    }

    @Override
    public Collection<ExpenseEntity> getAll() {
        return repository.getByUser(userService.getLoggedUser()).stream().sorted(Comparator.comparingLong(ExpenseEntity::getId)).toList();
    }
}