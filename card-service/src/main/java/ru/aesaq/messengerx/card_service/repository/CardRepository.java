package ru.aesaq.messengerx.card_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aesaq.messengerx.card_service.entity.Card;

import java.util.ArrayList;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    ArrayList<Card> getCardsByCreator(String creator);

    void removeById(Long id);

    Card getCardById(Long id);
}
