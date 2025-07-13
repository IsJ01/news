package com.app.news.auth.http.rest;

import org.springframework.web.bind.annotation.RestController;

import com.app.news.auth.dto.AuthRequest;
import com.app.news.auth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @PostMapping("/api/v1/auth/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Validated AuthRequest authRequest) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authenticationService.signUp(authRequest));
    }
    
    @PostMapping("/api/v1/auth/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Validated AuthRequest authRequest) throws Exception {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(authenticationService.signIn(authRequest));
    }

}
