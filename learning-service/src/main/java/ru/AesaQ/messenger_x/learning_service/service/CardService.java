package ru.AesaQ.messenger_x.learning_service.service;

import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.AesaQ.messenger_x.learning_service.entity.Card;
import ru.AesaQ.messenger_x.learning_service.repository.CardRepository;
import ru.AesaQ.messenger_x.learning_service.util.JwtUtil;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final JwtUtil jwtUtil;

    public CardService(CardRepository cardRepository, JwtUtil jwtUtil) {
        this.cardRepository = cardRepository;
        this.jwtUtil = jwtUtil;
    }

    public String createCard(Card cardWithoutId, String token) {
        String creator = jwtUtil.extractUsername(token);

        Card card = new Card();

        card.setCreator(creator);
        card.setStudy(cardWithoutId.getStudy());
        card.setAnswer(cardWithoutId.getAnswer());
        card.setExample1(cardWithoutId.getExample1());
        card.setExample2(cardWithoutId.getExample2());
        card.setExample3(cardWithoutId.getExample3());

        cardRepository.save(card);
        return "ok";
    }

    @Transactional
    public String removeCard(Long id, String token) {
        Card card = cardRepository.getCardById(id);
        String username = "";
        try {
            username = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return "Something went wrong...";
        }
        if (!card.getCreator().equals(username)) {
            return "nah.. you can't";
        }
        cardRepository.removeById(id);
        return "ok";
    }

    public String editCard(Long id, Card editedCard, String token) {
        String editor = jwtUtil.extractUsername(token);

        Card originalCard = cardRepository.getCardById(id);

        if (!originalCard.getCreator().equals(editor)) {
            return "nah.. you can't";
        }
        editedCard.setId(originalCard.getId());
        editedCard.setCreator(originalCard.getCreator());
        cardRepository.save(editedCard);
        return "";
    }
}
