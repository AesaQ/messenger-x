package ru.AesaQ.messenger_x.learning_service.controller;

import org.springframework.web.bind.annotation.*;
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
    public String createCard(@RequestBody Card card, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        return cardService.createCard(card, token);
    }

    @DeleteMapping("/remove/{id}")
    public String removeCard(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        return cardService.removeCard(id, token);
    }

    @PutMapping("/edit/{id}")
    public String editCard(@PathVariable Long id, @RequestBody Card card, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        return cardService.editCard(id, card, token);
    }
}
