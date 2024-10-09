package ru.aesaq.messengerx.leaderboardservice.service;

import org.springframework.stereotype.Service;
import ru.aesaq.messengerx.leaderboardservice.entity.Card;
import ru.aesaq.messengerx.leaderboardservice.repository.CardRepository;

import java.util.List;

@Service
public class LeaderboardService {
    private final CardRepository cardRepository;

    public LeaderboardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Object[]> getLeaderboardByCardsQuantity() {
        return cardRepository.findTop30CreatorsByQuantityCards();
    }

    public List<Card> getCardsByCreator(String creator) {
        return cardRepository.findCardsByCreator(creator);
    }
}
