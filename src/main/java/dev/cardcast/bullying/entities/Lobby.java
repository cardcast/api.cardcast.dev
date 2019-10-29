package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.util.AccessCodeGenerator;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class Lobby {
    private String code;

    private HashMap<Player, Boolean> queued = new HashMap<>();

    private boolean isPublic;

    private int maxPlayers;

    public Lobby(boolean isPublic, int maxPlayers) {
        this.isPublic = isPublic;
        this.maxPlayers = maxPlayers;
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
