package com.bankmanager.application.helpers.user;

import com.bankmanager.application.entities.user.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserHelper {

    public static Collection<UserDetails> toUserDetail(Collection<UserEntity> entities) {
        return entities.stream().map(UserHelper::toUserDetail).toList();
    }

    public static UserDetails toUserDetail(UserEntity entity) {
        return User.builder()
                .username(entity.getUsername())
                .password("{bcrypt}" + entity.getPassword())
                .roles(entity.getRoleStr())
                .build();
    }
}
