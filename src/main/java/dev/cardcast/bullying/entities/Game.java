package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private List<Player> players;

    private List<Card> deck;

    private List<Card> stack;

    @Setter
    private int turnIndex;

    @Setter
    private boolean clockwise;

    @Setter
    private int numberToDraw;

    public Game(List<Player> players){
        this.players = players;
        this.deck = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    public boolean isTheirTurn(Player player) {
        return turnIndex == players.indexOf(player);
    }

    public Card getTopCardFromDeck(){
        return deck.get(deck.size() - 1);
    }

    public Card getTopCardFromStack(){
        return stack.get(stack.size() - 1);
    }
}
