package ru.aesaq.messengerx.leaderboardservice.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.aesaq.messengerx.leaderboardservice.service.CacheService;

@Component
public class LeaderboardsUpdateRunner implements ApplicationRunner {
    private final CacheService service;

    public LeaderboardsUpdateRunner(CacheService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(() -> {
            service.getLeaderboardByCardsQuantity();
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                service.updateLeaderboardByCardsQuantity();
                System.out.println("кеш очищен");
            }
        });
        thread.start();
    }
}
