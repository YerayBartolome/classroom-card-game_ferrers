package com.drpicox.game.cards;

import com.drpicox.game.games.Game;
import com.drpicox.game.players.Player;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CardController {
    private final CardRepository cardRepository;
    private final RandomCardPicker randomCardPicker;

    public CardController(CardRepository cardRepository, RandomCardPicker randomCardPicker) {
        this.cardRepository = cardRepository;
        this.randomCardPicker = randomCardPicker;
    }

    public CardListFilter<Card> findByGame(Game game) {
        return new CardListFilter<>(cardRepository.findByGame(game));
    }

    public void createCards(Game game, String type, String name, int count) {
        for (var i = 0; i < count; i++)
            createCard(game, type, name);
    }

    private void createCard(Game game, String type, String name) {
        var card = new Card(game, type, name);
        cardRepository.save(card);
    }

    public void pickCards(Player player, int position, String type, int count) {
        for (var i = 0; i < count; i++)
            pickCard(player, position, type);
    }

    public Card pickCard(Player player, int square, String type) {
        var cards = cardRepository.findByOwnerAndType(null, type);
        var card = randomCardPicker.pickOne(cards);
        card.onPick(player, square);
        cardRepository.save(card);
        return card;
    }

    public void discardCard(Card card) {
        card.discard();
        cardRepository.save(card);
    }

    public void pileCard(Card card, String pile) {
        card.pile(pile);
        cardRepository.save(card);
    }

    public void moveCardToSquare(Card card, int square) {
        card.moveToSquare(square);
        cardRepository.save(card);
    }
}
