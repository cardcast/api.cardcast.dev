package dev.cardcast.bullying.util;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;

import java.util.ArrayList;
import java.util.List;

public class DeckGenerator {
    public static List<Card> generateBullyingDeck(){
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            if (suit != Suit.JOKER) {
                for (Rank rank : Rank.values()) {
                    if (rank != Rank.JOKER) {
                        deck.add(new Card(suit, rank));
                    }
                }
            }
        }
        for (int i = 0; i < 2; i++){
            deck.add(new Card(Suit.JOKER, Rank.JOKER));
        }
        return deck;
    }
}
