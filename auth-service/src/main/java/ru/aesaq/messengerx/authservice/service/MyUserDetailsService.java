package ru.aesaq.messengerx.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aesaq.messengerx.authservice.entity.User;
import ru.aesaq.messengerx.authservice.repository.UserRepository;
import ru.aesaq.messengerx.authservice.security.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        return new MyUserDetails(user);
    }
}
