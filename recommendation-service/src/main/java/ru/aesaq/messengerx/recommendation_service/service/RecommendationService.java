package ru.aesaq.messengerx.recommendation_service.service;

import org.springframework.stereotype.Service;
import ru.aesaq.messengerx.recommendation_service.entity.Card;
import ru.aesaq.messengerx.recommendation_service.entity.User;
import ru.aesaq.messengerx.recommendation_service.repository.CardRepository;
import ru.aesaq.messengerx.recommendation_service.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecommendationService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public RecommendationService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public List<Card> takeCards(int count, String creatorName, boolean isShuffle) {

        List<Card> cards = cardRepository.getCardsByCreatorOrderByRememberingTime(creatorName);
        User creator = userRepository.findUserByUsername(creatorName);

        ArrayList<Card> resultList = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();

        Iterator<Card> cardsIterator = cards.iterator();

        //Проверка знаний от Эббингауза
        while (cardsIterator.hasNext()) {
            if (resultList.size() >= count) {
                break;
            }
            Card card = cardsIterator.next();
            LocalDateTime nextEbbRepeatDate;
            try {
                nextEbbRepeatDate = LocalDateTime.parse(
                        getNextEbbRepeat(creator.getEbbLevel(), card.getEbbLevel(), card.getLastRepeat()));
            } catch (Exception e) {
                continue;
            }

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
        User creator = userRepository.findUserByUsername(cards.get(1).getCreator());
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime nextEbbRepeatDate;
        for (Card card : cards) {
            try {
                nextEbbRepeatDate = LocalDateTime.parse(
                        getNextEbbRepeat(creator.getEbbLevel(), card.getEbbLevel(), card.getLastRepeat()));
            } catch (Exception e) {
                continue;
            }
            if (currentDate.isAfter(nextEbbRepeatDate)) {
                if (card.getEbbLevel() == 0) {
                    card.setEbbLevel(1);
                }
                switch (card.getMemoryLevel()) {
                    case 1:
                        card.setEbbLevel(0);
                        break;
                    case 2:
                        card.setEbbLevel(1);
                        break;
                    case 3:
                        card.setEbbLevel(card.getEbbLevel() + 1);
                        break;
                }
            }
            card.setLastRepeat(currentDate.toString());
        }
        cardRepository.saveAll(cards);
    }

    private String getNextEbbRepeat(int UserEbbLevel, int CardEbbLevel, String last_repeat) {
        switch (UserEbbLevel) {
            case 1:
                switch (CardEbbLevel) {
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
                switch (CardEbbLevel) {
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
                switch (CardEbbLevel) {
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

