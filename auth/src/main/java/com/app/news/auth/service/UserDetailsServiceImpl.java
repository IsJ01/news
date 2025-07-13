package com.app.news.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.news.auth.database.repository.UserRepository;
import com.app.news.auth.dto.UserReadDto;
import com.app.news.auth.mapper.UserReadMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository usersRepository;
    private final UserReadMapper userReadMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserReadDto findById(Long id) {
        return usersRepository.findById(id)
            .map(userReadMapper::map)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid id"));
    }

}
