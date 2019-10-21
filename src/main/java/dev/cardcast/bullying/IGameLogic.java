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

    void drawCard(Game game, Player player, int amount);

    void initializeGame(Game game);

    void setNextPlayer(Game game);

    void endGame(Game game);
}
