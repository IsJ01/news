package com.app.news.news.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${token.signing.key}")
    private String token;

    public boolean isTokenValid(String token) {
        return !isExpirated(token);
    }

    public boolean isExpirated(String token) {
        return extractExpiration(token).before(new Date());
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        @SuppressWarnings("unchecked")
        List<GrantedAuthority> authorities = (ArrayList<GrantedAuthority>) claims.get("authorities");
        return authorities;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> function) {
        Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getToken())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getToken() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(token));
    }

}
