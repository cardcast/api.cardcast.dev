package dev.cardcast.bullying.entities.card;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter @AllArgsConstructor
public class Card {
    private Suit suit;
    private Rank rank;
}
