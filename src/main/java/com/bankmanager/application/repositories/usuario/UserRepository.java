package com.bankmanager.application.repositories.usuario;

import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.repositories.AbstractRepository;

public interface UserRepository extends AbstractRepository<UserEntity> {

    UserEntity getByUsername(String username);
}
