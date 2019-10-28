package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import lombok.Getter;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Game {

    @Getter
    private final String token;

    @Getter
    private List<Player> players;

    @Getter
    private List<Card> stack = new ArrayList<>();

    @Getter
    private List<Card> deck = new ArrayList<>();

    public Game(String token) {
        this.token = token;
        this.players = new ArrayList<>();
    }
}
