package ru.AesaQ.messenger_x.learning_service.service;

import org.springframework.stereotype.Service;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public String createCard(Card cardWithoutId) {
        Card card = new Card();
        card.setCreator(cardWithoutId.getCreator());
        card.setStudy(cardWithoutId.getStudy());
        card.setAnswer(cardWithoutId.getAnswer());
        card.setExample1(cardWithoutId.getExample1());
        card.setExample2(cardWithoutId.getExample2());
        card.setExample3(cardWithoutId.getExample3());

        cardRepository.save(card);
        return "ok";
    }

}
