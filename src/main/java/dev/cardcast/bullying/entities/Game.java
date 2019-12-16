package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Game {

    @Getter
    private final Host host;

    @Getter
    private List<Player> players;

    @Getter
    private List<Card> deck;

    @Getter
    private List<Card> stack;

    @Setter
    @Getter
    private int turnIndex;

    @Setter
    @Getter
    private boolean clockwise;

    @Setter
    @Getter
    private int numberToDraw;

    public Game(Host host, List<Player> players) {
        this.host = host;
        this.players = players;
        this.deck = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    public boolean isTheirTurn(Player player) {
        return turnIndex == players.indexOf(player);
    }

    public Card getTopCardFromDeck() {
        return deck.get(deck.size() - 1);
    }

    public Card getTopCardFromStack() {
        return stack.get(stack.size() - 1);
    }
}
