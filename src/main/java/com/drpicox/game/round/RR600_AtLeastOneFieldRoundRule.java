package com.drpicox.game.round;

import com.drpicox.game.cards.Card;
import com.drpicox.game.cards.CardController;
import com.drpicox.game.cards.CardListFilter;
import com.drpicox.game.players.Player;
import com.drpicox.game.players.PlayerController;
import org.springframework.stereotype.Component;

@Component
public class RR600_AtLeastOneFieldRoundRule extends EachPlayerRoundRule {

    public RR600_AtLeastOneFieldRoundRule(PlayerController playerController, CardController cardController) {
        super(playerController, cardController);
    }

    protected void runPlayer(Player player, CardListFilter<Card> allCards) {
        var fields = allCards.ofOwner(player).ofType("field").atAnySquare();

        if (fields.isEmpty())
            cardController.pickCard(player, 1, "field");
    }


}
