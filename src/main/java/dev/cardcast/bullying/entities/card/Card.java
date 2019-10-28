package dev.cardcast.bullying.entities.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter @AllArgsConstructor
public class Card {
    private Suit suit;
    private Rank rank;

    public static Card getCard(String cardString) {
        Suit suit = Suit.valueOf(cardString.split(" ")[0]);
        String stringRank = cardString.split(" ")[1];
        for (Rank rank : Rank.values()) {
            if (String.valueOf(rank.getRank()).equalsIgnoreCase(stringRank)) {
                return new Card(suit, rank);
            }
        }
        throw new IllegalArgumentException("NO CARD FOUND BY ID: " + cardString);
    }
}
