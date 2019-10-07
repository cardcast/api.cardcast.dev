package dev.cardcast.bullying.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lobby {
    @Getter
    private String code;

    @Getter
    private HashMap<Player, Boolean> queued = new HashMap<>();

    @Getter
    private int maxPlayers;

    @Getter
    private boolean isPublic;

    public Lobby(boolean isPublic, int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.isPublic = isPublic;
    }
}