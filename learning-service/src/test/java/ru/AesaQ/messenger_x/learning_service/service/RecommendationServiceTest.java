package ru.AesaQ.messenger_x.learning_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private RecommendationService recommendationService;
    @Captor
    private ArgumentCaptor<List<Card>> cardsCaptor;

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

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastDateOfEbbRepeat = null;
        Card lastCard = null;
        for (Card card : result) {

            if (LocalDateTime.parse(card.getNextEbbRepeat()).isBefore(currentTime)) {
                if (lastDateOfEbbRepeat == null) {
                    lastDateOfEbbRepeat = LocalDateTime.parse(card.getNextEbbRepeat());

                    assertThat(lastCard).isEqualTo(null);
                } else {
                    if (!LocalDateTime.parse(card.getNextEbbRepeat()).isEqual(lastDateOfEbbRepeat)) {
                        assertThat(LocalDateTime.parse(card.getNextEbbRepeat())).isAfter(lastDateOfEbbRepeat);
                    }
                }
                lastCard = card;
                continue;
            }
            if (lastCard == null) {
                lastCard = card;
                continue;
            }
            if (card.getMemoryLevel() == 1) {
                if (isCardOfEbbRepeat(lastCard)) {
                    lastCard = card;
                    continue;
                }
                if (lastCard.getMemoryLevel() > 1) {
                    fail("lastCard's memory level: " + lastCard.getMemoryLevel() + "; currentCard's memory level is 1");
                }
                lastCard = card;
                continue;
            } else if (card.getMemoryLevel() == 2) {
                if (isCardOfEbbRepeat(lastCard)) {
                    lastCard = card;
                    continue;
                }
                if (lastCard.getMemoryLevel() > 2) {
                    fail("lastCard's memory level: " + lastCard.getMemoryLevel() + "; currentCard's memory level is 2");
                }
                lastCard = card;
                continue;
            } else if (card.getMemoryLevel() == 3) {
                if (isCardOfEbbRepeat(lastCard)) {
                    lastCard = card;
                    continue;
                }
                if (lastCard.getMemoryLevel() > 3) {
                    fail("lastCard's memory level: " + lastCard.getMemoryLevel() + "; currentCard's memory level is 3");
                }
                lastCard = card;
                continue;
            }
            if (card.getMemoryLevel() >= 4) {
                if (lastCard.getMemoryLevel() <= 3) {
                    lastCard = card;
                    continue;
                }
                if (!LocalDateTime.parse(card.getLastRepeat())
                        .isEqual(LocalDateTime.parse(lastCard.getLastRepeat()))) {
                    assertThat(LocalDateTime.parse(card.getLastRepeat())
                            .isAfter(LocalDateTime.parse(lastCard.getLastRepeat())));
                }
                lastCard = card;
            }
        }
    }

    @Test
    public void testPutCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards = addNewCard(cards, 1, 3, true, LocalDateTime.now().minusMinutes(5).toString());
        cards = addNewCard(cards, 2, 3, true, LocalDateTime.now().minusMinutes(5).toString());
        cards = addNewCard(cards, 3, 3, true, LocalDateTime.now().minusMinutes(5).toString());
        cards = addNewCard(cards, 4, 3, true, LocalDateTime.now().minusMinutes(5).toString());
        cards = addNewCard(cards, 5, 3, true, LocalDateTime.now().minusMinutes(5).toString());

        Collections.shuffle(cards);

        recommendationService.putCards(cards);

        verify(cardRepository).saveAll(cardsCaptor.capture());
        List<Card> savedCards = cardsCaptor.getValue();
        for (Card card : savedCards) {
            switch (card.getMemoryLevel()) {
                case 1:
                    assertThat(card.getEbbLevel()).isEqualTo(0);
                    break;
                case 2:
                    assertThat(card.getEbbLevel()).isEqualTo(1);
                    break;
                case 3:
                    assertThat(card.getEbbLevel()).isEqualTo(2);
                    break;
                case 4:
                    assertThat(card.getEbbLevel()).isEqualTo(3);
                    break;
                case 5:
                    assertThat(card.getEbbLevel()).isEqualTo(4);
                    break;
            }
        }
    }

    private boolean isCardOfEbbRepeat(Card card) {
        LocalDateTime currentTime = LocalDateTime.now();
        return LocalDateTime.parse(card.getNextEbbRepeat()).isBefore(currentTime);
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
