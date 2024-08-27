package ru.AesaQ.messenger_x.learning_service.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/learn")
public class LearningController {

    @GetMapping("/my-info")
    public String getMyInfo(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        try {
            String username = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            Date expiration = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return username + " " + expiration;
        } catch (Exception e) {
            return "Something went wrong...";
        }
    }
}
