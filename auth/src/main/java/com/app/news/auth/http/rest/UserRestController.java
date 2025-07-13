package com.app.news.auth.http.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.news.auth.dto.UserReadDto;
import com.app.news.auth.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/public/users")
public class UserRestController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable Long id) {    
        return userDetailsServiceImpl.findById(id);
    }

}
