package com.bankmanager.application.entities.user;

import com.bankmanager.application.entities.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserEntity extends AbstractEntity {

    private String username;
    private String password;
    private String name;
}
