package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.util.AccessCodeGenerator;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Lobby extends PlayerContainer implements Serializable {

    private String code;

    private transient Host host;

    private boolean isPublic;

    private int maxPlayers;

    public Lobby(boolean isPublic, int maxPlayers, Host host) {
        super(new ArrayList<>());
        this.host = host;
        this.isPublic = isPublic;
        this.maxPlayers = maxPlayers;
        this.code = AccessCodeGenerator.generate();
    }

    public boolean addPlayer(Player player) {
        if (this.getPlayers().size() >= this.maxPlayers) {
            return false;
        }

        if (this.getPlayers().contains(player)) {
            return false;
        }

        this.getPlayers().add(player);
        return true;
    }
}
