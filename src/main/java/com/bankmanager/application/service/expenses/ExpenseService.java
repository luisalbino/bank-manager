package com.bankmanager.application.service.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.repositories.expenses.ExpenseRepository;
import com.bankmanager.application.service.AbstractService;
import com.bankmanager.application.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ExpenseService extends AbstractService<ExpenseEntity, ExpenseRepository> {

    private final UserService userService;

    protected ExpenseService(ExpenseRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    public void create(ExpenseEntity expense) {
        expense.setUser(userService.getLoggedUser());
        save(expense);
    }

    @Override
    public Collection<ExpenseEntity> getAll() {
        return repository.getByUser(userService.getLoggedUser());
    }
}
