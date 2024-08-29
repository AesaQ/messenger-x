package ru.AesaQ.messenger_x.learning_service.service;

import org.springframework.stereotype.Service;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationService {
    private final CardRepository cardRepository;

    public RecommendationService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> takeCards(int count, String creator) {

        List<Card> cards = cardRepository.getCardsByCreator(creator);

        Collections.shuffle(cards);

        ArrayList<Card> resultList = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        //Проверка знаний от Эббингауза
        for (Card card : cards) {
            if (resultList.size() >= count) {
                break;
            }
            LocalDate nextEbbRepeatDate = LocalDate.parse(card.getNextEbbRepeat());
            if (currentDate.isAfter(nextEbbRepeatDate)) {
                resultList.add(card);
            }
        }

        //Если пользователь очень плохо помнит (или вообще не помнит) слово
        //memory_level = 1
        for (Card card : cards) {
            if (resultList.size() >= count) {
                break;
            }
            if (card.getMemoryLevel() == 1) {
                resultList.add(card);
            }
        }
        //Пользователь еле помнит слово
        //memory_level = 2
        for (Card card : cards) {
            if (resultList.size() >= count) {
                break;
            }
            if (card.getMemoryLevel() == 2) {
                resultList.add(card);
            }
        }
        //Если пользователь средне помнит слово
        //memory_level = 3
        for (Card card : cards) {
            if (resultList.size() >= count) {
                break;
            }
            if (card.getMemoryLevel() == 3) {
                resultList.add(card);
            }
        }

        cards.sort(Comparator.comparing(Card::getLastRepeat));

        //по last_repeat
        for (Card card : cards) {
            if (resultList.size() >= count) {
                break;
            }
            resultList.add(card);
        }
        Collections.shuffle(resultList);

        return resultList;
    }
}
