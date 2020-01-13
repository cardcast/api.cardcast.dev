package dev.cardcast.bullying.entities.card;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Card {

    private Suit suit;
    private Rank rank;

    public static Card getCard(String cardString) {
        Suit finalSuit = null;

        String suitString = cardString.split(" ")[0];
        for (Suit suit : Suit.values()) {
            if (suit.name().equalsIgnoreCase(suitString)) {
                finalSuit = suit;
            }
        }
        String stringRank = cardString.split(" ")[1];
        for (Rank rank : Rank.values()) {
            if (rank.name().equalsIgnoreCase(stringRank)) {
                return new Card(finalSuit, rank);
            }
        }
        throw new IllegalArgumentException("NO CARD FOUND BY ID: " + cardString);
    }

    @Override
    public boolean equals(Object obj) {
        Card cardTwo = (Card) obj;
        return cardTwo.rank == this.rank && cardTwo.suit == this.suit;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.suit, this.rank);
    }
}
