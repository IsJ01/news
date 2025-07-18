package com.app.news.news.util;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTestUtil {

    @Value("${token.signing.key}")
    private String signingToken;

    public String generateToken() {
        return Jwts.builder()
            .subject("test_user")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(signingToken)))
            .compact();
    }

}
