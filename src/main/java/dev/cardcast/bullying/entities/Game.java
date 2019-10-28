package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Game implements EventListener {

    @Getter
    private final String token;
    @Getter
    private List<Player> players;
    @Getter
    private List<Card> deck;
    @Getter
    private List<Card> stack;
    @Getter @Setter
    private int turnIndex;
    @Getter @Setter
    private boolean clockwise;
    @Getter @Setter
    private int numberToDraw;

    public Game(String token) {
        this.token = token;
        players = new ArrayList<>();
        deck = new ArrayList<>();
        stack = new ArrayList<>();
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

    @EventHandler
    public void readyUp(Session session, PlayerReadyUpEvent event) {
        Player sessionPlayer = players.stream().filter(player -> player.getSession().equals(session)).findFirst().orElse(null);
        if (sessionPlayer != null) {
            sessionPlayer.setName(event.getName());
        }
    }
}
