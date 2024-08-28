package ru.AesaQ.messenger_x.learning_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.service.CardService;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public String createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }
}
