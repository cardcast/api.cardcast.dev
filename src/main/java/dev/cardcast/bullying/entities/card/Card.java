package dev.cardcast.bullying.entities.card;

import lombok.Getter;

public class Card {
    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    @Getter
    private Suit suit;

    @Getter
    private Rank rank;
}
