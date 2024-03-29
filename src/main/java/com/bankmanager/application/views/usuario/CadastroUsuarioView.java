package com.bankmanager.application.views.usuario;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.entities.user.UsuariosEntity;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.services.user.UserService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
public class CadastroUsuarioView extends VerticalLayout {

    private final UserService userService;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final Binder<UsuariosEntity> binder = new Binder<>();

    public CadastroUsuarioView(UserService userService, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userService = userService;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;

        binder.setBean(new UsuariosEntity());

        buildUI();
    }

    private void buildUI() {
        var fieldName = new TextField("Nome");
        fieldName.setRequired(true);
        binder.forField(fieldName)
                .withValidator(Objects::nonNull, "Nome precisa ser informado!")
                .bind(UsuariosEntity::getNome, UsuariosEntity::setNome);

        var fieldUsername = new TextField("Username");
        fieldUsername.setRequired(true);
        binder.forField(fieldUsername)
                .withValidator(Objects::nonNull, "Username precisa ser informado!")
                .bind(UsuariosEntity::getLogin, UsuariosEntity::setLogin);

        var fieldPassword = new PasswordField("Password");
        fieldPassword.setRequired(true);
        binder.forField(fieldPassword)
                .withValidator(Objects::nonNull, "Password precisa ser informado!")
                .bind(UsuariosEntity::getSenha, UsuariosEntity::setSenha);

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
