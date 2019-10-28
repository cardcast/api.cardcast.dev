package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;

public class CardRules {
    private static CardRules instance = null;

    public CardRules(){}

    public static CardRules getInstance()
    {
        if (instance == null)
            instance = new CardRules();

        return instance;
    }

    public boolean validPlay(Game game, Player player, Card playedCard){
        Suit suit = playedCard.getSuit();
        Rank rank = playedCard.getRank();

        Card topCard = game.getTopCardFromStack();

        if (game.getNumberToDraw() > 0) {
            if (!(rank == Rank.TWO || rank == Rank.JOKER )){
                return false; // while being bullied you can't play normal cards
            }
            if (rank.getRank() < topCard.getRank().getRank()){
                return false; // you can't bully yourself out of a bigger bully
            }
            if (suit == Suit.JOKER && player.getHand().getCards().size() == 1){
                return false; // you can not finish with a card that can always be played
            }
            return true; // this is a valid play (continue the bullying)
        }
        if (rank == Rank.JACK || suit == Suit.JOKER){
            if (player.getHand().getCards().size() == 1){
                return false; // you can not finish with a card that can always be played
            }
            return true; // this is a valid play (these cards can always be played after normal cards)
        }
        if (topCard.getSuit() == Suit.JOKER){
            return true; // you can play any card on top of a joker (provided you don't have to draw cards)
        }
        return rank == topCard.getRank() || suit == topCard.getSuit(); // normally you play a card of the same rank or suit (or both)
    }

    public boolean playCard(Game game, Player player, Card playedCard){
        boolean valid = validPlay(game, player, playedCard);
        if (!valid) { return false; } // gotta play valid cards bro

        // TODO: IMPLEMENT THE DIFFERENT RULES DIFFERENT CARDS HAVE. WHAT HAPPENS WHEN YOU PLAY THEM?
        // now every card is just regarded as being a non-special card for the purpose of what happens after playing it

        player.getHand().getCards().remove(playedCard); // there are certain cards you can play where it would not work like this!
        game.getStack().add(playedCard);

        passTurn(game); // there are certain cards you can play where this should NOT be called!
        return true;
    }

    public void passTurn(Game game){
        game.getPlayers().get(game.getTurnIndex()).setDoneDrawing(false);
        if(game.isClockwise()){
            game.setTurnIndex(game.getTurnIndex() + 1);
            if (game.getTurnIndex() == game.getPlayers().size()){
                game.setTurnIndex(0);
            }
        }
        else {
            game.setTurnIndex(game.getTurnIndex() - 1);
            if (game.getTurnIndex() == -1){
                game.setTurnIndex(game.getPlayers().size() - 1);
            }
        }
    }
}
