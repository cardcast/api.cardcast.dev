package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.interfaces.IGameManagerLogic;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements IGameManagerLogic {

    @Getter
    private List<Game> games = new ArrayList<>();

    @Getter
    private List<Lobby> lobbies = new ArrayList<>();


    @Override
    public Lobby findLobbyByCode(String code) {
        return this.lobbies.stream().filter(lobby -> lobby.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public Lobby createLobby(boolean isPublic, int maxPlayers) {
        Lobby lobby = new Lobby(isPublic, maxPlayers);
        this.lobbies.add(lobby);
        return lobby;
    }

    @Override
    public void addPlayer(Lobby lobby, Player player) {
        if(lobby.getMaxPlayers() >= lobby.queued.size()){
            return;
        }

        lobby.queued.add(player);
    }

    @Override
    public void removePlayer(Lobby lobby, Player player) {
        lobby.queued.remove(player);
    }

    @Override
    public void startGame(Lobby lobby) {
        this.games.add(new Game(lobby.queued));
        lobbies.remove(lobby);
    }
}
