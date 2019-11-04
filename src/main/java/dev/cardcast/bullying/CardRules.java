package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Hand;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;

import java.util.ArrayList;
import java.util.List;

public class CardRules {
    private static CardRules instance = null;

    public CardRules(){}

    public static CardRules getInstance() {
        if (instance == null)
            instance = new CardRules();

        return instance;
    }

    private void fillListWithOtherList(List<Card> stuffToAdd, List<Card> listToFill){
        for (int i = 0; i < stuffToAdd.size(); i++){
            listToFill.add(stuffToAdd.get(i));
        }
        stuffToAdd.clear();
    }

    private void swapHandWithPreviousHand(Player player, List<Card> newCards){
        Hand hand = player.getHand();
        List<Card> oldCards = new ArrayList<>();

        fillListWithOtherList(hand.getCards(), oldCards);
        fillListWithOtherList(newCards, hand.getCards());
        fillListWithOtherList(oldCards, newCards);
    }

    private void rotateAllHands(Game game){
        List<Player> players = game.getPlayers();
        List<Card> newCards = new ArrayList<>();
        if (game.isClockwise()){
            fillListWithOtherList(players.get(players.size() - 1).getHand().getCards(), newCards);
            for(int i = 0; i < players.size(); i++){
                swapHandWithPreviousHand(players.get(i), newCards);
            }
        }
        else {
            fillListWithOtherList(players.get(0).getHand().getCards(), newCards);
            for(int i = players.size() - 1; i >= 0; i--){
                swapHandWithPreviousHand(players.get(i), newCards);
            }
        }
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

        player.getHand().getCards().remove(playedCard);
        game.getStack().add(playedCard);

        // TODO: IMPLEMENT THE CHECK FOR VICTORY FOR THE CURRENT PLAYER

        switch (playedCard.getRank()){
            case TWO:
                // the next player will have to draw two (extra) cards
                game.setNumberToDraw(game.getNumberToDraw() + 2);
                passTurn(game);
                break;
            case JOKER:
                // the next player will have to draw five (extra) cards
                game.setNumberToDraw(game.getNumberToDraw() + 5);
                passTurn(game);
                break;
            case SEVEN:
                // you can play another card (essentially, your turn restarts)
                player.setDoneDrawing(false);
                // notice how the turn isn't passed here!
                break;
            case EIGHT:
                // the next player will have to skip their turn
                passTurn(game);
                passTurn(game);
                break;
            case ACE:
                // turn around the rotation of the players
                game.setClockwise(!game.isClockwise());
                passTurn(game);
                break;
            case TEN:
                // all players give their cards to the player on their left (if clockwise) or their right (if counter-clockwise)
                rotateAllHands(game);
                passTurn(game);
                break;
            case JACK:
                // This is a tricky one, the original rules say that you can play this card on anything
                // and then you get to choose what suit it becomes. will we keep this rule though...
                // TODO: IMPLEMENT THE EFFECTS OF JACK CARDS

                boolean doSomething = true;
                passTurn(game);
                break;
            default:
                // nothing special with these ranks
                passTurn(game);
                break;
        }
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
