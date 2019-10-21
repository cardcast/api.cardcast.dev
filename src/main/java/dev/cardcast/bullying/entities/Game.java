package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import dev.cardcast.bullying.network.messages.serverbound.lobby.SB_RequestLobbyMessage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Game implements EventListener {

    public Game(List<Player> players) {
        this.players = players;
    }

    @Getter
    private List<Player> players;

    @Getter
    private List<Card> stack = new ArrayList<>();

    @Getter
    private List<Card> deck = new ArrayList<>();

    @EventHandler
    public void readyUp(PlayerReadyUpEvent message) {

    }
}
