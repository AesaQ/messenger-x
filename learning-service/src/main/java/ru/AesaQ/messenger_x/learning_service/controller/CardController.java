package ru.AesaQ.messenger_x.learning_service.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.AesaQ.messenger_x.learning_service.service.CardService;

@RestController
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }


}
