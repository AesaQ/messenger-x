package ru.aesaq.messengerx.card_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.aesaq.messengerx.card_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}

