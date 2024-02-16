package com.bankmanager.application.repositories.expenses;

import com.bankmanager.application.entities.despesas.DesepesaEntity;
import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.repositories.AbstractRepository;

import java.util.List;

public interface DespesaRepository extends AbstractRepository<DesepesaEntity> {

    public List<DesepesaEntity> getByUser(UserEntity user);
}
