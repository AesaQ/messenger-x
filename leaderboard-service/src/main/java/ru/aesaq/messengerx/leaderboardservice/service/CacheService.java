package ru.aesaq.messengerx.leaderboardservice.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {
    private final LeaderboardService leaderboardService;

    public CacheService(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Cacheable("leaderboardByCardsQuantity")
    public List<Object[]> getLeaderboardByCardsQuantity() {
        return leaderboardService.getLeaderboardByCardsQuantity();
    }

    @CacheEvict("leaderboardByCardsQuantity")
    public void updateLeaderboardByCardsQuantity() {
    }
}
