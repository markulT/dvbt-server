package com.osmos.server.auth;

import com.osmos.server.auth.dto.LoginDTO;
import com.osmos.server.auth.exceptions.EmailAlreadyExistsException;
import com.osmos.server.auth.exceptions.RefreshException;
import com.osmos.server.auth.exceptions.UserDoesNotExistException;
import com.osmos.server.auth.exceptions.UserUnauthorizedException;
import com.osmos.server.redis.RedisTokenService;
import com.osmos.server.repo.RoleRepo;
import com.osmos.server.repo.UserRepo;
import com.osmos.server.schema.Role;
import com.osmos.server.schema.User;
import com.osmos.server.security.JwtProvider;
import com.osmos.server.security.JwtUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTokenService redisTokenService;

    public LoginDTO login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            User user = userRepo.getUserByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("Username with " + email + " not found");
            }

            JwtUser jwtUser = JwtUser.builder()
                    .username(email)
                    .password(password)
                    .status(user.getStatus())
                    .roles(user.getRoles())
                    .build();
            String token = jwtProvider.create(jwtUser);
            String refreshToken = jwtProvider.createRefresh(jwtUser);
            redisTokenService.setToken(user.getId().toString(), refreshToken);

            return LoginDTO.builder()
                    .email(email)
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .build();

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public Map<String, Object> register(String email, String password) {

        User user = userRepo.getUserByEmail(email);
        if (user != null) {
            throw new EmailAlreadyExistsException("User with email " + email + " already exists");
        }
        Role role = roleRepo.findRoleByName("ROLE_ADMIN");
        User newUser = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(List.of(role))
                .build();
        userRepo.save(newUser);
        JwtUser jwtUser = JwtUser.builder()
                .roles(newUser.getRoles())
                .status(newUser.getStatus())
                .password(newUser.getPassword())
                .username(newUser.getEmail())
                .build();
        String token = jwtProvider.create(jwtUser);
        String refreshToken = jwtProvider.createRefresh(jwtUser);
        redisTokenService.setToken(newUser.getId().toString(), refreshToken);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("email", email);
        responseBody.put("refreshToken", refreshToken);
        return responseBody;
    }

    public LoginDTO refresh(String refreshToken) {
        try {
            if (refreshToken == null) {
                throw new RefreshException("Refresh token is absent");
            }

            UUID userId = redisTokenService.getUserId(refreshToken);

            if (userId == null) {
                throw new UserUnauthorizedException("Such token is absent in database, that means user is not authorized");
            }
            User user = userRepo.findById(userId).orElseThrow(() -> new UserDoesNotExistException("User with such id does not exist"));
            boolean tokenIsValid = jwtProvider.validateRefresh(refreshToken);

            if (!tokenIsValid) {
                throw new RefreshException("incorrect token or user is not authorized currently");
            }

            JwtUser jwtUser = JwtUser.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .status(user.getStatus())
                    .roles(user.getRoles())
                    .build();
            String accessToken = jwtProvider.create(jwtUser);
            String newRefreshToken = jwtProvider.createRefresh(jwtUser);
            redisTokenService.setToken(user.getId().toString(), refreshToken);
//            Map<String, Object> tokenMap = new HashMap<>();
//            tokenMap.put("accessToken", accessToken);
//            tokenMap.put("refreshToken", newRefreshToken);
            return LoginDTO.builder()
                    .refreshToken(newRefreshToken)
                    .accessToken(accessToken)
                    .email(user.getEmail())
                    .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

}
