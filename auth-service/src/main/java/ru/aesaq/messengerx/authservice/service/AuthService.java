package ru.aesaq.messengerx.authservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aesaq.messengerx.authservice.entity.User;
import ru.aesaq.messengerx.authservice.repository.UserRepository;
import ru.aesaq.messengerx.authservice.util.JwtUtil;
import ru.aesaq.messengerx.authservice.validator.AuthValidator;

@Service
public class AuthService {
    public AuthService(UserRepository userRepository, AuthValidator authValidator, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authValidator = authValidator;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    private final UserRepository userRepository;
    private final AuthValidator authValidator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(User user) {
        String validateUsernameResult = authValidator.validateUsername(user.getUsername());
        String validatePasswordResult = authValidator.validatePassword(user.getPassword());

        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("username already exists");
        }
        if (validateUsernameResult != "ok") {
            return ResponseEntity.badRequest().body(validateUsernameResult);
        }
        if (validatePasswordResult != "ok") {
            return ResponseEntity.badRequest().body(validatePasswordResult);
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User registeredUser = userRepository.save(user);
        return ResponseEntity.ok(registeredUser);
    }

    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            String errorResponse = "Invalid username or password";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

class AuthenticationResponse {
    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return getJwt();
    }

    public String getJwt() {
        return jwt;
    }
}
