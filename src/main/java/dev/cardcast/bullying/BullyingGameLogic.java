package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.util.DeckGenerator;

import java.util.Collections;
import java.util.List;

public class BullyingGameLogic implements IGameLogic {
    private final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static BullyingGameLogic instance = null;
    private static CardRules rules = new CardRules();

    private BullyingGameLogic(){}
    public static BullyingGameLogic getInstance() {
        if (instance == null)
            instance = new BullyingGameLogic();

        return instance;
    }

    // region private internal functions

    private void onDeckEmpty(Game game) {
        List<Card> deck = game.getDeck();
        List<Card> stack = game.getStack();

        Card lastCard = game.getTopCardFromStack();
        deck.addAll(stack);
        deck.remove(lastCard);
        stack.clear();
        stack.add(lastCard);

        Collections.shuffle(deck);
    }

    private Card drawTopCard(Game game){
        if (game.getDeck().isEmpty()) {
            onDeckEmpty(game);
        }
        Card topCard = game.getTopCardFromDeck();
        game.getDeck().remove(topCard);
        return topCard;
    }

    private void distributeCardsAtStart(Game game){
        for (Player player : game.getPlayers()) {
            for (int i = 0; i < AMOUNT_OF_CARDS_PER_PLAYER; i++) {
                player.getHand().getCards().add(drawTopCard(game));
            }
        }
        game.getStack().add(drawTopCard(game));
    }

    // endregion

    // region public functions from Interface

    @Override
    public void startGame(Game game) {
        game.getDeck().clear();
        game.getStack().clear();
        game.getDeck().addAll(DeckGenerator.generateBullyingDeck());
        Collections.shuffle(game.getDeck());
        distributeCardsAtStart(game);

        game.setTurnIndex(0);
        game.setClockwise(true);
        game.setNumberToDraw(0);
    }

    @Override
    public boolean playCard(Game game, Player player, Card card){
        if (player.getHand().getCards().stream().noneMatch(card1 -> card1.equals(card))) return false;
        return rules.playCard(game, player, card);
    }

    @Override
    public boolean drawCard(Game game, Player player){
        if (!game.isTheirTurn(player) || player.isDoneDrawing()){
            return false; // Drawing cards is not allowed
        }
        if (game.getNumberToDraw() > 0) {
            // The player was bullied, so draw multiple cards
            for (int i = 0; i < game.getNumberToDraw(); i++) {
                player.getHand().getCards().add(drawTopCard(game));
            }
            game.setNumberToDraw(0);
        }
        else {
            // Just a normal draw
            player.getHand().getCards().add(drawTopCard(game));
            player.setDoneDrawing(true);
        }
        return true;
    }

    @Override
    public boolean endTurn(Game game, Player player) {
        if (!game.isTheirTurn(player) || !player.isDoneDrawing()){
            return false; // Ending the turn is not allowed
        }
        rules.passTurn(game);
        return true;
    }

    // endregion
}
