package com.bankmanager.application.repositories.expenses;

import com.bankmanager.application.entities.despesas.DespesasEntity;
import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.repositories.AbstractRepository;

import java.util.List;

public interface DespesaRepository extends AbstractRepository<DespesasEntity> {

    List<DespesasEntity> getByUser(UsuariosEntity user);
}
