package dev.cardcast.bullying.entities;

import lombok.Getter;

import java.util.List;

public abstract class PlayerContainer {

    private List<Player> players;

    public PlayerContainer(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

}
