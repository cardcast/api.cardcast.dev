package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hand {
    private List<Card> cards = new ArrayList<>();
}
