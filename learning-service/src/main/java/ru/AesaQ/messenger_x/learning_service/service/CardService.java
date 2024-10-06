package ru.AesaQ.messenger_x.learning_service.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public String createCard(Card cardWithoutId, String creator) {

        Card card = new Card();

        card.setCreator(creator);
        card.setStudy(cardWithoutId.getStudy());
        card.setAnswer(cardWithoutId.getAnswer());
        card.setExample1(cardWithoutId.getExample1());
        card.setExample2(cardWithoutId.getExample2());
        card.setExample3(cardWithoutId.getExample3());
        card.setEbbLevel(0);
        card.setMemoryLevel(1);

        cardRepository.save(card);
        return "ok";
    }

    @Transactional
    public String removeCard(Long id, String username) {
        Card card = cardRepository.getCardById(id);
        if (!card.getCreator().equals(username)) {
            return "nah.. you can't do this";
        }
        cardRepository.removeById(id);
        return "ok";
    }

    public String editCard(Long id, Card editedCard, String editor) {
        Card originalCard = cardRepository.getCardById(id);

        if (!originalCard.getCreator().equals(editor)) {
            return "nah.. you can't do this";
        }
        editedCard.setId(originalCard.getId());
        editedCard.setCreator(originalCard.getCreator());
        cardRepository.save(editedCard);
        return "";
    }
}
