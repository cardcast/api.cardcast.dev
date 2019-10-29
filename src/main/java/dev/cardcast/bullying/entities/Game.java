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
public class Game implements EventListener {

    private final String token;

    private List<Player> players;

    private List<Card> deck;
    public Card getTopCardFromDeck(){
        return deck.get(deck.size() - 1);
    }

    private List<Card> stack;
    public Card getTopCardFromStack(){
        return stack.get(stack.size() - 1);
    }

    @Setter
    private int turnIndex;
    public boolean isTheirTurn(Player player) {
        return turnIndex == players.indexOf(player);
    }

    @Setter
    private boolean clockwise;

    @Setter
    private int numberToDraw;

    public Game(String token) {
        this.token = token;
        players = new ArrayList<>();
        deck = new ArrayList<>();
        stack = new ArrayList<>();
    }

    @EventHandler
    public void readyUp(Session session, PlayerReadyUpEvent event) {
        Player sessionPlayer = players.stream().filter(player -> player.getSession().equals(session)).findFirst().orElse(null);
        if (sessionPlayer != null) {
            sessionPlayer.setName(event.getName());
        }
    }
}
