package com.bankmanager.application.entities.user;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.enums.user.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "usuario")
public class UsuariosEntity extends AbstractEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public String getRoleStr() {
        return this.role.name();
    }
}
