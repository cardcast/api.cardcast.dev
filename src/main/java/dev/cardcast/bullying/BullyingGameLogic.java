package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

import java.util.Collections;

public class BullyingGameLogic implements IGameLogic {
    private final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static BullyingGameLogic instance = null;

    private BullyingGameLogic(){}

    private void shuffleStackToDeck(Game game) {
        Card lastCard = game.getTopCardFromStack();
        game.getDeck().addAll(game.getStack());
        game.getDeck().remove(lastCard);

        game.getStack().clear();
        game.getStack().add(lastCard);
        shuffleDeck(game);
    }

    private Card drawTopCard(Game game){
        if (game.getDeck().isEmpty()) {
            shuffleStackToDeck(game);
        }
        Card topCard = game.getDeck().get(game.getDeck().size()-1);
        game.getDeck().remove(topCard);
        return topCard;
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
            for (int i = 0; i < AMOUNT_OF_CARDS_PER_PLAYER; i++) {
                player.getHand().getCards().add(drawTopCard(game));
            }
        }
    }

    @Override
    public boolean playCard(Game game, Player player, Card card){
        if (!player.getHand().getCards().contains(card)) return false;

        // TODO: ADD ALL MUTATIONS TO THE GAME OF EVERY CARD THAT AFFECTS GAMEPLAY;

        // playing a card should automatically call endTurn(), unless the player is allowed to do something else
        return false;
    }

    @Override
    public boolean drawCard(Game game, Player player){
        if (!game.hisTurn(player) || player.isHasDrawn()){
            // Drawing cards is not allowed
            return false;
        }
        if (game.numberToDraw > 0) {
            // The player was bullied, so draw multiple cards
            for (int i = 0; i < game.numberToDraw; i++) {
                player.getHand().getCards().add(drawTopCard(game));
            }
            game.numberToDraw = 0;
        }
        else {
            // Just a normal draw
            player.getHand().getCards().add(drawTopCard(game));
            player.setHasDrawn(true);
        }
        return true;
    }

    @Override
    public boolean endTurn(Game game, Player player) {
        if (!game.hisTurn(player) || !player.isHasDrawn()){
            // Ending the turn is not allowed
            return false;
        }

        //TODO: NOW THE TURN SHOULD BE PASSED TO THE NEXT PLAYER

        return true;
    }

    @Override
    public void startGame(Game game) {
    }

    @Override
    public void initializeGame(Game game) {

    }

    @Override
    public void endGame(Game game) {

    }
}
