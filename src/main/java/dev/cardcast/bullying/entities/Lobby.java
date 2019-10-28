package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.util.AccessCodeGenerator;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;

public class Lobby implements Serializable {
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
        this.code = AccessCodeGenerator.generate();
    }

    public boolean addPlayer(Player player){
        if(this.queued.size() >= this.maxPlayers){
            return false;
        }

        if(this.queued.get(player) != null){
            return false;
        }

        this.queued.put(player, false);
        return true;
    }
}