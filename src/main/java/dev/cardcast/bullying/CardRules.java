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

        if (game.numberToDraw > 0) {
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
        return rank == topCard.getRank() || suit == topCard.getSuit(); // normally you play a card of the same rank or suit (or both)
    }

    public boolean playCard(Game game, Player player, Card playedCard){
        boolean valid = validPlay(game, player, playedCard);
        if (!valid) { return false; } // gotta play valid cards bro

        return false; // not implemented
    }
}
