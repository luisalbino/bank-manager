package com.bankmanager.application.repositories.user;

import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.repositories.AbstractRepository;

public interface UserRepository extends AbstractRepository<UserEntity> {

    UserEntity getByUsername(String username);
}
