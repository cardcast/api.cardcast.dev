package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

public interface IGameLogic {
    void startGame(Game game);

    void shuffleDeck(Game game);

    void shuffleStack(Game game);

    void distributeCards(Game game);

    boolean playCard(Game game, Player player, Card card);

    boolean drawCard(Game game, Player player);

    boolean endTurn(Game game, Player player);

    void initializeGame(Game game);

    void endGame(Game game);
}
