package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements IGameManager {

    private List<Game> games = new ArrayList<>();

    private List<Lobby> lobbies = new ArrayList<>();

    @Override
    public List<Lobby> getLobbies() {
        return new ArrayList<>(this.lobbies);
    }

    @Override
    public List<Game> getGames() {
        return new ArrayList<>(this.games);
    }

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
    public Lobby createLobby(boolean isPublic, int maxPlayers, Host host) {
        Lobby lobby = new Lobby(isPublic, maxPlayers, host);
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
    public boolean startGame(Lobby lobby) {
        for (boolean isReady : lobby.getQueued().values()) {
            if (!isReady) {
                return false;
            }
        }
        Game game = new Game(new ArrayList<>(lobby.getQueued().keySet()));
        this.games.add(game);
        lobbies.remove(lobby);
        return true;
    }
}

