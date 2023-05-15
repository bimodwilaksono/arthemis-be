package com.enigma.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String JwtSecret;
    @Value("${jwt.expiration}")
    private Integer JwtExpiration;

    public String generateToken(String subject){
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JwtSecret)
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpiration));

        return builder.compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e){
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException e){
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException e){
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException e){
            throw new RuntimeException("JWT claims string is empty");
        } catch (SignatureException e){
            throw new RuntimeException("Invalid JWT signature");
        }
    }
}
