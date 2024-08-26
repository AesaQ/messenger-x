package ru.aesaq.messengerx.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aesaq.messengerx.authservice.entity.User;
import ru.aesaq.messengerx.authservice.service.AuthService;
import ru.aesaq.messengerx.authservice.service.AuthenticationRequest;
import ru.aesaq.messengerx.authservice.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    public UserController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authService.login(authenticationRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.register(user);
    }

    @GetMapping("/my-info")
    public String getInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return jwtUtil.extractUsername(token) + " " + jwtUtil.extractExpiration(token);
    }
}