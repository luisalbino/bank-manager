package com.bankmanager.application.repositories.usuario;

import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.repositories.AbstractRepository;

public interface UsuarioRepository extends AbstractRepository<UsuariosEntity> {

    UsuariosEntity getByLogin(String login);
}
