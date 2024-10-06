package ru.aesaq.messengerx.leaderboardservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Leaderboard {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
