package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

import java.util.List;

public interface IGameLogic {
    void startGame(Game game);

    boolean playCard(Game game, Player player, Card card);

    List<Card> drawCard(Game game, Player player);

    boolean endTurn(Game game, Player player);

}
