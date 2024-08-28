package ru.AesaQ.messenger_x.learning_service.service;

import org.springframework.stereotype.Service;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


}
