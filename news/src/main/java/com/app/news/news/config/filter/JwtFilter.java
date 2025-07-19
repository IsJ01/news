package com.app.news.news.config.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.news.news.service.JwtService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private static final String AUTH_PREFIX = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String auth_header = request.getHeader(AUTH_PREFIX);
        if (StringUtils.isEmpty(auth_header) || !auth_header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        auth_header = auth_header.substring(BEARER_PREFIX.length());

        if (!jwtService.isTokenValid(auth_header) || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        final String username = jwtService.extractUsername(auth_header);
        List<GrantedAuthority> authorities = jwtService.extractAuthorities(auth_header);
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            User.withUsername(username).password("").authorities(authorities).build(), 
            null,
            authorities
        );

        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }

}
