package com.formapp.damnjan.utils;

import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static com.formapp.damnjan.config.SecurityConstants.*;

@Component
public class JWTUtils {

    private final SecretKey key;
    private final UserRepository userRepository;

    public JWTUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
        byte[] keyByte = Base64.getDecoder().decode(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyByte, SECRET_KEY_ALGORITHM);
    }

    public String generateToken(UserDetails userDetails) {

        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(ExceptionSupplier.usernameNotFound);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .claim("role", userEntity.getRole())
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {

        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(ExceptionSupplier.usernameNotFound);

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .claim("role", userEntity.getRole())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {

        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
