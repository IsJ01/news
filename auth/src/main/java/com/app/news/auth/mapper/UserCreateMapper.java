package com.app.news.auth.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.news.auth.database.entity.User;
import com.app.news.auth.dto.AuthRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<AuthRequest, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(AuthRequest object) {
        return User.builder()
            .username(object.getUsername())
            .password(passwordEncoder.encode(object.getPassword()))
            .build();
    }

}
