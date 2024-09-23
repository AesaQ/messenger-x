package ru.AesaQ.messenger_x.learning_service.service;

import org.springframework.stereotype.Service;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecommendationService {
    private final CardRepository cardRepository;

    public RecommendationService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> takeCards(int count, String creator, boolean isShuffle) {

        List<Card> cards = cardRepository.getCardsByCreator(creator);

        Collections.shuffle(cards);

        ArrayList<Card> resultList = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();

        Iterator<Card> cardsIterator = cards.iterator();

        //Проверка знаний от Эббингауза
        while (cardsIterator.hasNext()) {
            if (resultList.size() >= count) {
                break;
            }
            Card card = cardsIterator.next();
            LocalDateTime nextEbbRepeatDate = LocalDateTime.parse(card.getNextEbbRepeat());
            if (currentDate.isAfter(nextEbbRepeatDate)) {
                resultList.add(card);
                cardsIterator.remove();
            }
        }

        //Если пользователь очень плохо помнит (или вообще не помнит) слово
        //memory_level = 1
        cardsIterator = cards.iterator();
        while (cardsIterator.hasNext()) {
            if (resultList.size() >= count) {
                break;
            }
            Card card = cardsIterator.next();

            if (card.getMemoryLevel() == 1) {
                resultList.add(card);
                cardsIterator.remove();

            }
        }

        //Пользователь еле помнит слово
        //memory_level = 2
        cardsIterator = cards.iterator();
        while (cardsIterator.hasNext()) {
            if (resultList.size() >= count) {
                break;
            }
            Card card = cardsIterator.next();

            if (card.getMemoryLevel() == 2) {
                resultList.add(card);
                cardsIterator.remove();

            }
        }

        //Если пользователь средне помнит слово
        //memory_level = 3
        cardsIterator = cards.iterator();
        while (cardsIterator.hasNext()) {
            if (resultList.size() >= count) {
                break;
            }
            Card card = cardsIterator.next();

            if (card.getMemoryLevel() == 3) {
                resultList.add(card);
                cardsIterator.remove();

            }
        }

        //по last_repeat
        cards.sort(Comparator.comparing(Card::getLastRepeat));
        cardsIterator = cards.iterator();
        while (cardsIterator.hasNext()) {
            if (resultList.size() >= count) {
                break;
            }
            Card card = cardsIterator.next();

            resultList.add(card);
            cardsIterator.remove();

        }
        if (isShuffle) {
            Collections.shuffle(resultList);
        }

        return resultList;
    }

    public void putCards(List<Card> cards) {
        LocalDateTime currentDate = LocalDateTime.now();

        for (Card card : cards) {
            LocalDateTime nextEbbRepeatDate = LocalDateTime.parse(card.getNextEbbRepeat());
            if (currentDate.isAfter(nextEbbRepeatDate)) {
                if (card.getEbbLevel() == 0) {
                    card.setEbbLevel(1);
                }
                switch (card.getMemoryLevel()) {
                    case 1:
                        card.setEbbLevel(0);
                    case 2:
                        card.setEbbLevel(1);
                    case 3:
                        card.setEbbLevel(card.getEbbLevel() - 1);
                    case 4:
                        card.setEbbLevel(card.getEbbLevel());
                    case 5:
                        card.setEbbLevel(card.getEbbLevel() + 1);
                }
            }
            card.setLastRepeat(currentDate.toString());
        }
        cardRepository.saveAll(cards);
    }
}
