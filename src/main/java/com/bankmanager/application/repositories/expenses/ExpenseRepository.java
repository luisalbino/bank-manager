package com.bankmanager.application.repositories.expenses;

import com.bankmanager.application.entities.expenses.ExpenseEntity;
import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.repositories.AbstractRepository;

import java.util.List;

public interface ExpenseRepository extends AbstractRepository<ExpenseEntity> {

    public List<ExpenseEntity> getByUser(UserEntity user);
}
