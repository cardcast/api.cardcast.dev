package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

import java.util.Collections;

public class BullyingGameLogic implements IGameLogic {
    private final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static BullyingGameLogic instance = null;

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
        game.getDeck().addAll(game.getStack());
        game.getDeck().remove(lastCard);

        game.getStack().clear();
        game.getStack().add(lastCard);
        Collections.shuffle(game.getDeck());
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
        if (!player.getHand().getCards().contains(card)) return false;

        return true;
    }

    @Override
    public boolean drawCard(Game game, Player player){
        player.getHand().getCards().add(drawTopCard(game));

        return true;
    }

    @Override
    public void startGame(Game game) {
        Collections.shuffle(game.getDeck());
        distributeCards(game);
    }
}
