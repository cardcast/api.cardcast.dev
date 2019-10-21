package dev.cardcast.bullying.interfaces;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

public interface IGameLogic {
    void shuffleDeck(Game game);
    void distributeCards(Game game);
    void playCard(Game game, Card card);
    void drawCard(Game game, Player player, int amount);
    void startGame(Game game);
    void initializeGame(Game game);
    void setNextPlayer(Game game);
    void endGame(Game game);
}
