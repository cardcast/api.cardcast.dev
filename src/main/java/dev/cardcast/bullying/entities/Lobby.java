package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.util.AccessCodeGenerator;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Lobby implements Serializable {
    
    private String code;

    private List<Player> queued;

    private transient Host host;

    private boolean isPublic;

    private int maxPlayers;

    public Lobby(boolean isPublic, int maxPlayers, Host host) {
        this.host = host;
        this.isPublic = isPublic;
        this.maxPlayers = maxPlayers;
        this.code = AccessCodeGenerator.generate();
        this.queued = new ArrayList<>();
    }

    public boolean addPlayer(Player player) {
        if (this.queued.size() >= this.maxPlayers) {
            return false;
        }

        if (this.queued.contains(player)) {
            return false;
        }

        this.queued.add(player);
        return true;
    }
}
