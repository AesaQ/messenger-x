package ru.aesaq.messengerx.authservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aesaq.messengerx.authservice.entity.User;
import ru.aesaq.messengerx.authservice.repository.UserRepository;
import ru.aesaq.messengerx.authservice.validator.AuthValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthValidator authValidator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegister_UsernameExists() {
        User user = new User();
        user.setUsername("existingUsername");
        user.setPassword("password");
        when(authValidator.validateUsername(anyString())).thenReturn("ok");
        when(authValidator.validatePassword(anyString())).thenReturn("ok");
        when(userRepository.existsByUsername("existingUsername")).thenReturn(true);

        ResponseEntity<?> response = authService.register(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("username already exists");
    }

    @Test
    public void testRegister_invalidUsername() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(authValidator.validatePassword(anyString())).thenReturn("ok");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        when(authValidator.validateUsername(anyString())).thenReturn("username is not ok");

        ResponseEntity<?> response = authService.register(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("username is not ok");
    }

    @Test
    public void testRegister_invalidPassword() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(authValidator.validateUsername(anyString())).thenReturn("ok");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        when(authValidator.validatePassword(anyString())).thenReturn("password is not ok");

        ResponseEntity<?> response = authService.register(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("password is not ok");
    }

}
