package ru.aesaq.messengerx.authservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aesaq.messengerx.authservice.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
