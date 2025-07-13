package com.app.news.auth.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {
    Long id;
    String username;
    Collection<? extends GrantedAuthority> authorities;
}
