package com.example.haruplay.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.expiration.refresh-token}")
    private long refreshTokenValidityInMilliseconds;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userEmail) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .claim("userEmail", userEmail)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userEmail) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .claim("userEmail", userEmail)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUserEmail(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build().parseClaimsJws(token)
                    .getBody();

            return claims.get("userEmail", String.class);
        } catch (Exception e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.: {}", e.getMessage());
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다. : {}", e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.warn("잘못된 JWT 토큰입니다: {}", e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            log.warn("JWT 토큰 서명이 유효하지 않습니다: {}", e.getMessage());
        } catch (Exception e) {
            log.warn("JWT 토큰 검증에 실패했습니다: {}", e.getMessage());
        } return false;
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT Refresh token validation failed: {}", e.getMessage());
            return false;
        }
    }

}
