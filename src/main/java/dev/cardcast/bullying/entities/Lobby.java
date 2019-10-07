package dev.cardcast.bullying.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    @Getter
    private String code;

    public List<Player> queued = new ArrayList<>();

    @Getter
    private int maxPlayers;

    @Getter
    private boolean isPublic;

    public Lobby(boolean isPublic, int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.isPublic = isPublic;
    }
}