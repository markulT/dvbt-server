package com.osmos.server.security;

import com.osmos.server.schema.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtProvider {
    public Claims getClaims(String token);
    public String create(User user);

    public String create(JwtUser jwtUser);

    public boolean validate(String token);

    public boolean validateRefresh(String refreshToken);
    public String createRefresh(JwtUser jwtUser);
    public Claims getClaimsRefresh(String token);

}
