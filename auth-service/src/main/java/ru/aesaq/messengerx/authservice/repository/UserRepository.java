package ru.aesaq.messengerx.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aesaq.messengerx.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);

    User findUserByUsername(String username);

    boolean existsByUsername(String username);
}
