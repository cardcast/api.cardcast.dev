package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.LobbySettings;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.interfaces.ILobbyLogic;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager implements ILobbyLogic {

    @Getter
    private List<Lobby> lobbies = new ArrayList<>();


    @Override
    public Lobby findLobbyByCode(String code) {
        return this.lobbies.stream().filter(lobby -> lobby.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public Lobby createLobby(LobbySettings settings) {
        Lobby lobby = new Lobby(settings);
        this.lobbies.add(lobby);
        return lobby;
    }

    @Override
    public void addPlayer(Lobby lobby, Player player) {

    }

    @Override
    public void removePlayer(Lobby lobby, Player player) {

    }

    @Override
    public void startGame(Lobby lobby) {

    }


}
