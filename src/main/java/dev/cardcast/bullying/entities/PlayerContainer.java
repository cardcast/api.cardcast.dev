package dev.cardcast.bullying.entities;

import java.util.List;
import java.util.UUID;

public abstract class PlayerContainer {

    private List<Player> players;

    public PlayerContainer(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
