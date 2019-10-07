package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.interfaces.ILobbyLogic;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    @Getter
    private Game game;

    @Getter
    private String code;

    @Getter
    private List<Player> queued = new ArrayList<>();

    @Getter
    private LobbySettings lobbySettings;

    public Lobby(LobbySettings settings) {
        this.lobbySettings = settings;
    }

    public Lobby() {
        this.lobbySettings = new LobbySettings();
    }
}