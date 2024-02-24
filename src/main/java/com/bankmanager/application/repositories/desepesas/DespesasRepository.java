package com.bankmanager.application.repositories.desepesas;

import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.repositories.AbstractRepository;

import java.util.List;

public interface DespesasRepository extends AbstractRepository<DespesaEntity> {

    List<DespesaEntity> getByUsuario(UsuariosEntity usuario);
}
