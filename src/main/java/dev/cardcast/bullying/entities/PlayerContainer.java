package dev.cardcast.bullying.entities;

import java.util.List;
import java.util.UUID;

public abstract class PlayerContainer {

    private List<UUID> players;

    public PlayerContainer(List<UUID> players) {
        this.players = players;
    }

    public List<UUID> getPlayers() {
        return this.players;
    }
}
