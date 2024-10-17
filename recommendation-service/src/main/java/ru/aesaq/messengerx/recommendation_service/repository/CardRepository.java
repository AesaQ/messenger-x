package ru.aesaq.messengerx.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aesaq.messengerx.recommendation_service.entity.Card;

import java.util.ArrayList;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    ArrayList<Card> getCardsByCreatorOrderByRememberingTime(String creator);

    void removeById(Long id);

    Card getCardById(Long id);
}
