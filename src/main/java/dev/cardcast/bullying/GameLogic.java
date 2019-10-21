package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.interfaces.IGameLogic;

import java.util.Collections;

public class GameLogic implements IGameLogic {
    final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static GameLogic instance = null;

    private GameLogic(){}

    private Card drawTopCard(Game game){
        if (game.getDeck().isEmpty()) {
            shuffleSteckToDeck(game);
        }
        Card topCard = game.getDeck().get(game.getDeck().size()-1);
        game.getDeck().remove(topCard);
        return topCard;
    }

    private void shuffleSteckToDeck(Game game) {
        //todo Refactor deze <3
        Card lastCard = game.getTopCardFromStack();
        game.getDeck().addAll(game.getStack());
        game.getDeck().remove(lastCard);

        game.getStack().clear();
        game.getStack().add(lastCard);
        shuffleDeck(game);
    }

    @Override
    public void shuffleDeck(Game game){
        Collections.shuffle(game.getDeck());
    }

    @Override
    public void distributeCards(Game game){
        for (Player player : game.getPlayers()) {
            drawCard(game, player, AMOUNT_OF_CARDS_PER_PLAYER);
        }
    }

    @Override
    public void playCard(Game game, Card card){
        //todo checks
    }

    @Override
    public void drawCard(Game game, Player player, int amount){
        for(int i=0;i<=amount;i++){
            player.getHand().getCards().add(drawTopCard(game));
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void initializeGame() {

    }

    @Override
    public void setNextPlayer() {

    }
}
