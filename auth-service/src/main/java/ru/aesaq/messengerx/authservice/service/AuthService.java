package ru.aesaq.messengerx.authservice.service;

import org.springframework.stereotype.Service;
import ru.aesaq.messengerx.authservice.entity.User;
import ru.aesaq.messengerx.authservice.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
