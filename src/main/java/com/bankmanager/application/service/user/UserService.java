package com.bankmanager.application.service.user;

import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.enums.user.RoleEnum;
import com.bankmanager.application.helpers.BCryptHelper;
import com.bankmanager.application.helpers.user.UserHelper;
import com.bankmanager.application.repositories.user.UserRepository;
import com.bankmanager.application.service.AbstractService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService extends AbstractService<UserEntity, UserRepository> {

    protected UserService(UserRepository repository) {
        super(repository);
    }

    public Collection<UserDetails> getDetails() {
        return UserHelper.toUserDetail(this.getAll());
    }

    public void createUser(UserEntity user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        beforeCreate(user);
        this.save(user);
        afterCreate(user, inMemoryUserDetailsManager);
    }

    public void beforeCreate(UserEntity user) {
        Collection<String> usernamesInUse = this.getAll().stream().map(UserEntity::getUsername).toList();

        if (usernamesInUse.contains(user.getUsername())) {
            throw new IllegalArgumentException("Username já está em uso!");
        }

        user.setRole(RoleEnum.ADMIN);
        user.setPassword(BCryptHelper.toHash(user.getPassword()));
    }

    public void afterCreate(UserEntity user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        var username = user.getUsername();
        if (!inMemoryUserDetailsManager.userExists(username)) {
            inMemoryUserDetailsManager.createUser(UserHelper.toUserDetail(user));
        }
    }
}
