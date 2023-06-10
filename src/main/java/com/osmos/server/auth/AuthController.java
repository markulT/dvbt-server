package com.osmos.server.auth;

import com.osmos.server.auth.dto.LoginDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody() LoginRequestDto body, HttpServletResponse response) {

        LoginDTO login = authService.login(body.getEmail(), body.getPassword());

        HttpHeaders headers = new HttpHeaders();

        long cookieAge = 2_592_000_000L;

        headers.add("Set-Cookie", "refreshToken=" + login.getRefreshToken() + "; HttpOnly; SameSite=none; Secure; Path=/;Max-Age=2592000000");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(login);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody() LoginRequestDto body) {
        return ResponseEntity.ok().body(authService.register(body.getEmail(), body.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@CookieValue(name = "refreshToken") String refreshToken) {


        LoginDTO loginDTO = authService.refresh(refreshToken);

        HttpHeaders headers = new HttpHeaders();

        long cookieAge = 2_592_000_000L;

        headers.add("Set-Cookie", "refreshToken=" + loginDTO.getRefreshToken() + "; HttpOnly; SameSite=none; Secure; Path=/;Max-Age=2592000000");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(loginDTO);
    }


}
