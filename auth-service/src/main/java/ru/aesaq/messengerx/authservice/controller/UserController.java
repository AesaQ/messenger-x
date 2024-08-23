package ru.aesaq.messengerx.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aesaq.messengerx.authservice.entity.User;
import ru.aesaq.messengerx.authservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        //Autogeneration id
        User newUser = new User(user.getUsername(), user.getPassword(), user.getProfilePicture());

        User registeredUser = authService.register(newUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User authorizedUser = authService.login(user.getUsername(), user.getPassword());
        if (authorizedUser != null) {
            return ResponseEntity.ok(authorizedUser);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
