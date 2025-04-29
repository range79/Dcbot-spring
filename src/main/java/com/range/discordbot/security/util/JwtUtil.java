package com.range.discordbot.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Service
public class JwtUtil {

    @Value(value = "${app.jwt.secret-key}")
    private String token;

    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    @Value(value = "${app.jwt.duration}")
    private int duration;
    public SecretKey key(){
        byte[] encodedKey = Base64.getDecoder().decode(token);
        return Keys.hmacShaKeyFor(encodedKey);
    }
    public String generateToken(String username,String Role,String jti){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + duration*1000))
                .id(jti)
                .signWith(key()).compact();

    }
    public Claims parseToken(String token){
        try {
            return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
        }catch (Exception e){
            log.error("invalid token: {}",e.getMessage());
            return null;
        }

    }
    public String getUsername(String token){
        Claims claims = parseToken(token);
        if(claims == null){
            log.error("Claims is null");
            return null;
        }
        return claims.getSubject();
    }



}
