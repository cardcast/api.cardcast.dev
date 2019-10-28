package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements IGameManager {

    @Getter
    private List<Game> games = new ArrayList<>();

    @Getter
    private List<Lobby> lobbies = new ArrayList<>();

    @Override
    public Lobby tryJoinLobby(Player player, String code) {
        for (Lobby lobby : this.lobbies) {
            if (lobby.getCode().equals(code)) {
                lobby.getQueued().put(player, false);
                return lobby;
            }
        }
        return null;
    }

    @Override
    public Lobby createLobby(boolean isPublic, int maxPlayers) {
        Lobby lobby = new Lobby(isPublic, maxPlayers);
        this.lobbies.add(lobby);
        return lobby;
    }

    @Override
    public void addPlayer(Lobby lobby, Player player) {
        lobby.addPlayer(player);
    }

    @Override
    public void removePlayer(Lobby lobby, Player player) {
        lobby.getQueued().remove(player);
    }

    @Override
    public boolean playerReadyUp(Lobby lobby, Player player) {
        lobby.getQueued().put(player, true);
        return lobby.getQueued().get(player);
    }

    @Override
    public Game startGame(Lobby lobby) {
        for (boolean isReady : lobby.getQueued().values()) {
            if (!isReady) {
                return null;
            }
        }
        Game game = new Game(lobby.getCode());
        game.getPlayers().addAll(lobby.getQueued().keySet());
        this.games.add(game);
        lobbies.remove(lobby);
        return game;
    }
}

