package ru.AesaQ.messenger_x.learning_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    public List<Card> testTakeCards() {

    }


    private ArrayList<Card> addNewCards(ArrayList<Card> elementsList, int memoryLevel, int ebbLevel, boolean nextEbbRepeatIsPast, String lastRepeat, int cardsCount) {
        int maxListNumber = 0;
        if (elementsList != null) {
            for (Card card : elementsList) {
                if (card.getId() > maxListNumber) {
                    maxListNumber += card.getId();
                }
            }
        }
        LocalDateTime lastRepeatDate = LocalDateTime.parse(lastRepeat);
        for (int i = 0; i < cardsCount; i++) {
            Card newCard = new Card();
            newCard.setId((long) (++maxListNumber));
            newCard.setMemoryLevel(memoryLevel);
            newCard.setEbbLevel(ebbLevel);
            if (ebbLevel != 0 && nextEbbRepeatIsPast) {
                newCard.setNextEbbRepeat("1970-01-01T00:00:00");
            }
            newCard.setLastRepeat(lastRepeatDate.minusMinutes(1).toString());
            newCard.setCreator("test");
            newCard.setAnswer("test");
            newCard.setStudy("test");
            elementsList.add(newCard);
        }
        return elementsList;
    }
}
