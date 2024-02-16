package com.bankmanager.application.services.user;

import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.enums.user.RoleEnum;
import com.bankmanager.application.helpers.BCryptHelper;
import com.bankmanager.application.helpers.usuario.UserHelper;
import com.bankmanager.application.repositories.usuario.UserRepository;
import com.bankmanager.application.services.AbstractService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class UserService extends AbstractService<UsuariosEntity, UserRepository> {

    protected UserService(UserRepository repository) {
        super(repository);
    }

    public Collection<UserDetails> getDetails() {
        return UserHelper.toUserDetail(this.getAll());
    }

    public void createUser(UsuariosEntity user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        beforeCreate(user);
        this.save(user);
        afterCreate(user, inMemoryUserDetailsManager);
    }

    public void beforeCreate(UsuariosEntity user) {
        Collection<String> usernamesInUse = this.getAll().stream().map(UsuariosEntity::getLogin).toList();

        if (usernamesInUse.contains(user.getLogin())) {
            throw new IllegalArgumentException("Username já está em uso!");
        }

        user.setRole(RoleEnum.ADMIN);
        user.setSenha(BCryptHelper.toHash(user.getSenha()));
    }

    public void afterCreate(UsuariosEntity user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        var username = user.getLogin();
        if (!inMemoryUserDetailsManager.userExists(username)) {
            inMemoryUserDetailsManager.createUser(UserHelper.toUserDetail(user));
        }
    }

    public void logout(UI ui) {
        ui.getPage().setLocation("/");
        var logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }

    public UsuariosEntity getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public UsuariosEntity getLoggedUser() {
        UsuariosEntity result = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            result = getByUsername(authentication.getName());
        }

        if (Objects.isNull(result)) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        return result;
    }
}
