package com.osmos.server.security;

import com.osmos.server.schema.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProviderImpl implements JwtProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.refreshSecret}")
    private String refreshSecret;
    //    @Value("jwt.token.validityTimeInMilliseconds")
    @Value("${jwt.token.refreshValidityInMills}")
    private long refreshValidityInMills;
    private final long validityTime = 20000000;

    private final UserDetailsService userDetailsService;


    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token).getBody();
        return claims;
    }

    @Override
    public String create(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRoles());
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + validityTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
        return token;
    }

    @Override
    public String create(JwtUser jwtUser) {
        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        List<String> rolesList = jwtUser.getRoles().stream().map(role -> role.getName()).toList();
        claims.put("roles", rolesList);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + validityTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            if (getClaims(token).getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    @Override
    public Claims getClaimsRefresh(String token) {
        log.info("started in here");
        log.info(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(refreshSecret.getBytes())
                .build()
                .parseClaimsJws(token).getBody();
        log.info("Worked for now");
        System.out.println(claims.getExpiration());
        return claims;

    }

    @Override
    public String createRefresh(JwtUser jwtUser) {
        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        List<String> rolesList = jwtUser.getRoles().stream().map(role -> role.getName()).toList();
        claims.put("roles", rolesList);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + validityTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, refreshSecret.getBytes())
                .compact();
        return token;
    }

    @Override
    public boolean validateRefresh(String refreshToken) {
        try {

            if(getClaimsRefresh(refreshToken).getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Jwt token is expired or invalid");
        }
    }
}
