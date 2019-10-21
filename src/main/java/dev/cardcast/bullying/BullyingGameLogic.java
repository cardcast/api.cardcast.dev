package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

import java.util.Collections;

public class BullyingGameLogic implements IGameLogic {
    private final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static BullyingGameLogic instance = null;

    private BullyingGameLogic(){}

    private Card drawTopCard(Game game){
        if (game.getDeck().isEmpty()) {
            shuffleStackToDeck(game);
        }
        Card topCard = game.getDeck().get(game.getDeck().size()-1);
        game.getDeck().remove(topCard);
        return topCard;
    }

    private void shuffleStackToDeck(Game game) {
        Card lastCard = game.getTopCardFromStack();
        game.getDeck().addAll(game.getStack());
        game.getDeck().remove(lastCard);

        game.getStack().clear();
        game.getStack().add(lastCard);
        shuffleDeck(game);
    }

    @Override
    public void shuffleStack(Game game){
        Collections.shuffle(game.getStack());
    }

    @Override
    public void shuffleDeck(Game game) {
        Collections.shuffle(game.getDeck());
    }

    @Override
    public void distributeCards(Game game){
        for (Player player : game.getPlayers()) {
            drawCard(game, player, AMOUNT_OF_CARDS_PER_PLAYER);
        }
    }

    @Override
    public boolean playCard(Game game, Player player, Card card){
        if (!player.getHand().getCards().contains(card)) return false;

        // TODO: ADD ALL MUTATIONS TO THE GAME OF EVERY CARD THAT AFFECTS GAMEPLAY;
        
    }

    @Override
    public void drawCard(Game game, Player player, int amount){
        for(int i=0;i<=amount;i++){
            player.getHand().getCards().add(drawTopCard(game));
        }
    }

    @Override
    public void startGame(Game game) {
    }

    @Override
    public void initializeGame(Game game) {

    }

    @Override
    public void setNextPlayer(Game game) {

    }

    @Override
    public void endGame(Game game) {

    }
}
