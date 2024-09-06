package ru.AesaQ.messenger_x.learning_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.service.RecommendationService;
import ru.AesaQ.messenger_x.learning_service.util.JwtUtil;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/learn")
public class LearningController {
    private final JwtUtil jwtUtil;
    private final RecommendationService recommendationService;

    public LearningController(JwtUtil jwtUtil, RecommendationService recommendationService) {
        this.jwtUtil = jwtUtil;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/take-cards")
    public ResponseEntity<?> takeCards(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("you are not logged in");
        }
        return ResponseEntity.ok(recommendationService.takeCards(15, username, true));
    }

    @PostMapping("/put-cards")
    public ResponseEntity<?> putCards(@RequestHeader("Authorization") String token, @RequestBody List<Card> cards) {
        token = token.replace("Bearer ", "");
        String username;
        try {
            username = jwtUtil.extractUsername(token);
            for (Card card : cards) {
                if (!username.equals(card.getCreator())) {
                    throw new AccessDeniedException("There are cards in the list that you don't have access to");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        recommendationService.putCards(cards);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/my-info")
    public String getMyInfo(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        try {
            String username = jwtUtil.extractUsername(token);
            Date expiration = jwtUtil.extractExpiration(token);
            return username + " " + expiration;
        } catch (Exception e) {
            return "Something went wrong...";
        }
    }
}
