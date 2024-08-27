package ru.AesaQ.messenger_x.learning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.AesaQ.messenger_x.learning_service.entity.Card;

import java.util.ArrayList;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    ArrayList<Card> getCardsByCreator(String creator);
}
