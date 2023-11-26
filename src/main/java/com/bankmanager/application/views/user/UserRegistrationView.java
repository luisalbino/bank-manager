package com.bankmanager.application.views.user;

import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.views.AbstractView;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@AnonymousAllowed
@PageTitle("Registrar-se")
public class UserRegistrationView extends AbstractView {

    private final Binder<UserEntity> binder = new Binder<>();

    public UserRegistrationView() {
        binder.setBean(new UserEntity());

        var fUsername = new TextField("Username");
        binder.forField(fUsername).bind(UserEntity::getUsername, UserEntity::setUsername);

        var fPassword = new TextField("Password");
        binder.forField(fUsername).bind(UserEntity::getPassword, UserEntity::setPassword);

        add(fUsername, fPassword);
    }
}
