package com.bankmanager.application.helpers.usuario;

import com.bankmanager.application.entities.user.UsuariosEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserHelper {

    public static Collection<UserDetails> toUserDetail(Collection<UsuariosEntity> entities) {
        return entities.stream().map(UserHelper::toUserDetail).toList();
    }

    public static UserDetails toUserDetail(UsuariosEntity entity) {
        return User.builder()
                .username(entity.getLogin())
                .password("{bcrypt}" + entity.getSenha())
                .roles(entity.getRoleStr())
                .build();
    }
}
