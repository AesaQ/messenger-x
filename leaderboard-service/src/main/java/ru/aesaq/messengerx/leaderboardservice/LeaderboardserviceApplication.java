package ru.aesaq.messengerx.leaderboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@SpringBootApplication
@Cacheable
public class LeaderboardserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderboardserviceApplication.class, args);
	}

}
