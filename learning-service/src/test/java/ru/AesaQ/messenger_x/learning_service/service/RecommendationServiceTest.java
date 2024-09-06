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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    public void testTakeCards() {
        ArrayList<Card> cards = new ArrayList<>();

        // nextEbbRepeatIsPast = true
        cards = addNewCard(cards, 5, 2, true, LocalDateTime.now().minusMinutes(5).toString());

        //memory_level = 1
        cards = addNewCard(cards, 1, 2, false, LocalDateTime.now().minusMinutes(5).toString());

        //memory_level = 2
        cards = addNewCard(cards, 2, 2, false, LocalDateTime.now().minusMinutes(5).toString());

        //memory_level = 3
        cards = addNewCard(cards, 3, 2, false, LocalDateTime.now().minusMinutes(5).toString());

        //last_repeat
        cards = addNewCard(cards, 5, 2, false, LocalDateTime.now().minusMinutes(19).toString());
        cards = addNewCard(cards, 5, 2, false, LocalDateTime.now().minusMinutes(16).toString());
        cards = addNewCard(cards, 5, 2, false, LocalDateTime.now().minusMinutes(13).toString());
        cards = addNewCard(cards, 5, 2, false, LocalDateTime.now().minusMinutes(12).toString());
        cards = addNewCard(cards, 5, 2, false, LocalDateTime.now().minusMinutes(10).toString());

        //shuffle
        Collections.shuffle(cards);

        when(cardRepository.getCardsByCreator(anyString())).thenReturn(cards);

        List<Card> result = recommendationService.takeCards(15, "test", false);

        assertThat(result.size()).isEqualTo(9);

        long index = 0;
        for (Card card : result) {
            ++index;
            assertThat(card.getId()).isEqualTo(index);
        }
    }


    private ArrayList<Card> addNewCard(ArrayList<Card> elementsList, int memoryLevel, int ebbLevel, boolean nextEbbRepeatIsPast, String lastRepeat) {
        long maxListNumber = 0;
        if (elementsList.size() != 0) {
            for (Card card : elementsList) {
                if (card.getId() > maxListNumber) {
                    maxListNumber = card.getId();
                }
            }
        }
        LocalDateTime lastRepeatDate = LocalDateTime.parse(lastRepeat);
        Card newCard = new Card();
        maxListNumber++;
        newCard.setId(maxListNumber);
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
        return elementsList;
    }
}
