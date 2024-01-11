package com.bankmanager.application.views.user;

import com.bankmanager.application.components.CustomButton;
import com.bankmanager.application.entities.user.UserEntity;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.service.user.UserService;
import com.bankmanager.application.views.AbstractView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Objects;

@Route("register")
@AnonymousAllowed
@PageTitle("Registrar-se")
public class UserRegistrationView extends AbstractView {

    private final UserService userService;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final Binder<UserEntity> binder = new Binder<>();

    public UserRegistrationView(UserService userService, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userService = userService;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;

        binder.setBean(new UserEntity());

        buildUI();
    }

    private void buildUI() {
        var fieldName = new TextField("Nome");
        fieldName.setRequired(true);
        binder.forField(fieldName)
                .withValidator(Objects::nonNull, "Nome precisa ser informado!")
                .bind(UserEntity::getName, UserEntity::setName);

        var fieldUsername = new TextField("Username");
        fieldUsername.setRequired(true);
        binder.forField(fieldUsername)
                .withValidator(Objects::nonNull, "Username precisa ser informado!")
                .bind(UserEntity::getUsername, UserEntity::setUsername);

        var fieldPassword = new PasswordField("Password");
        fieldPassword.setRequired(true);
        binder.forField(fieldPassword)
                .withValidator(Objects::nonNull, "Password precisa ser informado!")
                .bind(UserEntity::getPassword, UserEntity::setPassword);

        var register = new CustomButton("Cadastrar-se");
        register.addClickListener(event -> {
            try {
                this.userService.createUser(binder.getBean(), this.inMemoryUserDetailsManager);
                NotificationHelper.success("Cadastro realizado com sucesso!");
                getUI().get().navigate("login");
            } catch (Exception ex) {
                NotificationHelper.error(ex.getMessage());
            }
        });

        add(fieldName, fieldUsername, fieldPassword, register);
    }
}
