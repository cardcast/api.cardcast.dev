package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements IGameManager {

    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

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
                lobby.getQueued().add(player);
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
    public Game startGame(Lobby lobby) {
        Game game = new Game(lobby.getHost(), lobby.getQueued());
        this.games.add(game);
        BullyingGameLogic.getInstance().startGame(game);
        lobbies.remove(lobby);
        return game;
    }
}

