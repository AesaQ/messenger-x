package ru.aesaq.messengerx.leaderboardservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.aesaq.messengerx.leaderboardservice.entity.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c.creator, COUNT(c.creator) AS count FROM Card c GROUP BY c.creator ORDER BY count DESC LIMIT 30")
    List<Object[]> findTop30CreatorsByQuantityCards();

    List<Card> findCardsByCreator(String creator);
}
