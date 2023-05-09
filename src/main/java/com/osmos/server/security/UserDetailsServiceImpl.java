package com.osmos.server.security;

import com.osmos.server.repo.UserRepo;
import com.osmos.server.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByEmail(username);
        JwtUser jwtUser = JwtUser.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .status(user.getStatus())
                .build();
        return jwtUser;
    }
}
