package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Game(List<Player> players){
        this.players = players;
    }

    @Getter
    private List<Player> players = new ArrayList<>();

    @Getter
    private List<Card> stack = new ArrayList<>();

    @Getter
    private List<Card> deck = new ArrayList<>();

    private int turnIndex = 0;
    private boolean clockwise = true;

}
