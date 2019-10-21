package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

public interface IGameLogic {
    void startGame(Game game);

    boolean playCard(Game game, Player player, Card card);

    boolean drawCard(Game game, Player player);
}
