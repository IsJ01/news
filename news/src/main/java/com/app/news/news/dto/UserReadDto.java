package com.app.news.news.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Value;

@Value
public class UserReadDto {
    Long id;
    String username;
    List<GrantedAuthority> authorities;
}
