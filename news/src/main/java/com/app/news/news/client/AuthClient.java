package com.app.news.news.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.news.news.dto.UserReadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthClient {
    
    @Value("${rest.client.auth-server}")
    private String auth_server;

    private final RestTemplate restTemplate;

    public UserReadDto fetchUserById(Long id) {
        ResponseEntity<UserReadDto> responseEntity = restTemplate.getForEntity(
            String.format("%s/api/v1/public/users/%s", auth_server, id),
            UserReadDto.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return null;
        }
    } 

}
