package dev.cardcast.bullying.entities.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Suit {
    CLUBS("C"),
    DIAMONDS("D"),
    HEARTS("H"),
    SPADES("S"),
    JOKER("J");


    @Getter
    private final String suit;

}
