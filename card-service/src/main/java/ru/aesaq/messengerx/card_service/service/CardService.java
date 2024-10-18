package ru.aesaq.messengerx.card_service.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.aesaq.messengerx.card_service.entity.Card;
import ru.aesaq.messengerx.card_service.entity.User;
import ru.aesaq.messengerx.card_service.repository.CardRepository;
import ru.aesaq.messengerx.card_service.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public String createCard(Card cardWithoutId, String creatorName) {

        User creator = userRepository.findUserByUsername(creatorName);

        Card card = new Card();

        card.setCreator(creatorName);
        card.setStudy(cardWithoutId.getStudy());
        card.setAnswer(cardWithoutId.getAnswer());
        card.setExample1(cardWithoutId.getExample1());
        card.setExample2(cardWithoutId.getExample2());
        card.setExample3(cardWithoutId.getExample3());
        card.setEbbLevel(0);
        card.setMemoryLevel(1);
        card.setNextEbbRepeat(getNextEbbRepeat(creator.getEbbLevel(), 0, "1970-01-01T00:00:00"));
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
        editedCard.setNextEbbRepeat(originalCard.getNextEbbRepeat());
        cardRepository.save(editedCard);
        return "";
    }

    private String getNextEbbRepeat(int userEbbLevel, int cardEbbLevel, String last_repeat) {
        if (userEbbLevel == 0) {
            userEbbLevel = 3;
        }
        switch (userEbbLevel) {
            case 1:
                switch (cardEbbLevel) {
                    case 0:
                        return "1970-01-01T00:00:00";
                    case 1:
                        return LocalDateTime.parse(last_repeat).plusMinutes(3).toString();
                    case 2:
                        return LocalDateTime.parse(last_repeat).plusMinutes(10).toString();
                    case 3:
                        return LocalDateTime.parse(last_repeat).plusMinutes(20).toString();
                    case 4:
                        return LocalDateTime.parse(last_repeat).plusMinutes(30).toString();
                    case 5:
                        return LocalDateTime.parse(last_repeat).plusHours(6).toString();
                    case 6:
                        return LocalDateTime.parse(last_repeat).plusHours(12).toString();
                    case 7:
                        return LocalDateTime.parse(last_repeat).plusHours(18).toString();
                    case 8, 9:
                        return LocalDateTime.parse(last_repeat).plusDays(1).toString();
                    case 10, 11:
                        return LocalDateTime.parse(last_repeat).plusDays(2).toString();
                    case 12, 13:
                        return LocalDateTime.parse(last_repeat).plusDays(3).toString();
                    case 14, 15:
                        return LocalDateTime.parse(last_repeat).plusDays(5).toString();
                    case 16, 17:
                        return LocalDateTime.parse(last_repeat).plusDays(7).toString();
                    case 18, 19:
                        return LocalDateTime.parse(last_repeat).plusDays(10).toString();
                    case 20, 21:
                        return LocalDateTime.parse(last_repeat).plusDays(14).toString();
                    case 22, 23:
                        return LocalDateTime.parse(last_repeat).plusDays(20).toString();
                    case 24, 25:
                        return LocalDateTime.parse(last_repeat).plusDays(30).toString();
                }
                break;
            case 2:
                switch (cardEbbLevel) {
                    case 0:
                        return "1970-01-01T00:00:00";
                    case 1:
                        return LocalDateTime.parse(last_repeat).plusMinutes(10).toString();
                    case 2:
                        return LocalDateTime.parse(last_repeat).plusMinutes(30).toString();
                    case 3:
                        return LocalDateTime.parse(last_repeat).plusHours(12).toString();
                    case 4:
                        return LocalDateTime.parse(last_repeat).plusDays(1).toString();
                    case 5:
                        return LocalDateTime.parse(last_repeat).plusDays(2).toString();
                    case 6:
                        return LocalDateTime.parse(last_repeat).plusDays(3).toString();
                    case 7:
                        return LocalDateTime.parse(last_repeat).plusDays(5).toString();
                    case 8:
                        return LocalDateTime.parse(last_repeat).plusDays(7).toString();
                    case 9:
                        return LocalDateTime.parse(last_repeat).plusDays(10).toString();
                    case 10:
                        return LocalDateTime.parse(last_repeat).plusDays(14).toString();
                    case 11:
                        return LocalDateTime.parse(last_repeat).plusDays(20).toString();
                    case 12:
                        return LocalDateTime.parse(last_repeat).plusDays(30).toString();
                }
                break;
            case 3:
                switch (cardEbbLevel) {
                    case 0:
                        return "1970-01-01T00:00:00";
                    case 1:
                        return LocalDateTime.parse(last_repeat).plusMinutes(30).toString();
                    case 2:
                        return LocalDateTime.parse(last_repeat).plusDays(1).toString();
                    case 3:
                        return LocalDateTime.parse(last_repeat).plusDays(3).toString();
                    case 4:
                        return LocalDateTime.parse(last_repeat).plusDays(7).toString();
                    case 5:
                        return LocalDateTime.parse(last_repeat).plusDays(14).toString();
                    case 6:
                        return LocalDateTime.parse(last_repeat).plusDays(30).toString();
                }
                break;
        }
        return "learned";
    }
}
