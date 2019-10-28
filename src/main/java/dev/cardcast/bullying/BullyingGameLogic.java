package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;
import dev.cardcast.bullying.util.CardStackGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BullyingGameLogic implements IGameLogic {
    private final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static BullyingGameLogic instance = null;
    private static CardRules rules = new CardRules();

    private BullyingGameLogic(){}

    public static BullyingGameLogic getInstance()
    {
        if (instance == null)
            instance = new BullyingGameLogic();

        return instance;
    }

    private Card drawTopCard(Game game){
        if (game.getDeck().isEmpty()) {
            onDeckEmpty(game);
        }

        Card topCard = game.getDeck().get(game.getDeck().size()-1);
        game.getDeck().remove(topCard);
        return topCard;
    }

    private void onDeckEmpty(Game game) {
        Card lastCard = game.getTopCardFromStack();
        List<Card> deck = game.getDeck();
        List<Card> stack = game.getStack();

        deck.addAll(stack);
        stack.clear();
        stack.add(lastCard);

        deck.remove(lastCard);

        Collections.shuffle(deck);
    }

    private void distributeCards(Game game){
        for (Player player : game.getPlayers()) {
            for (int i = 0; i < AMOUNT_OF_CARDS_PER_PLAYER; i++) {
                player.getHand().getCards().add(drawTopCard(game));
            }
        }
    }

    @Override
    public boolean playCard(Game game, Player player, Card card){
        if (player.getHand().getCards().stream().noneMatch(card1 -> card1.equals(card))) return false;

        rules.playCard(game, player, card);

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
        game.getDeck().addAll(CardStackGenerator.generateBullyingStack());
        Collections.shuffle(game.getDeck());
        distributeCards(game);

        game.getStack().add(drawTopCard(game));
    }
}
