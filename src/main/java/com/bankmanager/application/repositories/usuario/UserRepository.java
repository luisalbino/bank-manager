package com.bankmanager.application.repositories.usuario;

import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.repositories.AbstractRepository;

public interface UserRepository extends AbstractRepository<UsuariosEntity> {

    UsuariosEntity getByUsername(String username);
}
