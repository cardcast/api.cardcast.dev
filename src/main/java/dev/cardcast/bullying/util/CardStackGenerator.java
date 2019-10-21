package dev.cardcast.bullying.util;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;

import java.util.ArrayList;
import java.util.List;

public class CardStackGenerator {
    public static List<Card> generateBullyingStack(){
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if(rank != Rank.JOKER && suit != Suit.JOKER){
                    deck.add(new Card(suit,rank));
                }
            }
        }

        deck.add(new Card(Suit.JOKER, Rank.JOKER));
        deck.add(new Card(Suit.JOKER, Rank.JOKER));

        return deck;
    }
}
