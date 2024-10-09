package ru.aesaq.messengerx.leaderboardservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aesaq.messengerx.leaderboardservice.service.CacheService;
import ru.aesaq.messengerx.leaderboardservice.service.LeaderboardService;
import ru.aesaq.messengerx.leaderboardservice.util.JwtUtil;


@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final JwtUtil jwtUtil;
    private final LeaderboardService leaderboardService;
    private final CacheService cacheService;

    public LeaderboardController(JwtUtil jwtUtil, LeaderboardService leaderboardService, CacheService cacheService) {
        this.jwtUtil = jwtUtil;
        this.leaderboardService = leaderboardService;
        this.cacheService = cacheService;
    }

    @GetMapping("/get_all")
    private ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        try {
            jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("you are not logged in");
        }
        return ResponseEntity.ok(cacheService.getLeaderboardByCardsQuantity());
    }

    @GetMapping("/cards_of_creator/{creator}")
    private ResponseEntity<?> getCardsByCreator(@PathVariable("creator") String creator) {
        return ResponseEntity.ok(leaderboardService.getCardsByCreator(creator));
    }
}
