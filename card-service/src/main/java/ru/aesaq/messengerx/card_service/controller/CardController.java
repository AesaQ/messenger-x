package ru.aesaq.messengerx.card_service.controller;

import org.springframework.web.bind.annotation.*;
import ru.aesaq.messengerx.card_service.entity.Card;
import ru.aesaq.messengerx.card_service.service.CardService;
import ru.aesaq.messengerx.card_service.util.JwtUtil;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;
    private final JwtUtil jwtUtil;

    public CardController(CardService cardService, JwtUtil jwtUtil) {
        this.cardService = cardService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create")
    public String createCard(@RequestBody Card card, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String creator;
        try {
            creator = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return "Something went wrong...";
        }
        return cardService.createCard(card, creator);
    }

    @DeleteMapping("/remove/{id}")
    public String removeCard(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return "Something went wrong...";
        }
        return cardService.removeCard(id, username);
    }

    @PutMapping("/edit/{id}")
    public String editCard(@PathVariable Long id, @RequestBody Card card, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String editor;
        try {
            editor = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return "Something went wrong...";
        }
        return cardService.editCard(id, card, editor);
    }
}
