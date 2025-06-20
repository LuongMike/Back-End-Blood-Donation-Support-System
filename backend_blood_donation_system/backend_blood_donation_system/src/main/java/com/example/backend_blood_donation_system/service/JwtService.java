package com.example.backend_blood_donation_system.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.entity.TokenBlacklist;
import com.example.backend_blood_donation_system.repository.TokenBlacklistRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final TokenBlacklistRepository tokenBlacklistRepository;
    
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long expirationTime;


    public JwtService(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }


    // //create a JWT token for the given username
    // public String generateToken(String username, String role) {
    //     return Jwts.builder()
    //             .setSubject(username)
    //             .claim("role", role)
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
    //             .signWith(SignatureAlgorithm.HS256, secretKey)
    //             .compact();
    // }

    public String generateToken(Integer userId, String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
}


    //take username from the JWT token
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //check token was expired or not
    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    //check token in the blacklist or not
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }

    //add token to the blacklist
    public void blacklistToken(String token) {
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        tokenBlacklistRepository.save(blacklist);
    }

    public String extractRole(String token) {
    return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
    }

    public Integer extractUserId(String token) {
    Object idClaim = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .get("userId");
    
    if (idClaim == null) {
        throw new IllegalArgumentException("Token không chứa userId");
    }

    return Integer.parseInt(idClaim.toString());
    }

}
