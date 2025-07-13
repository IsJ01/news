package com.app.news.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class AuthRequest {

    @NotEmpty
    String username;

    @NotEmpty
    String password;
    
}
