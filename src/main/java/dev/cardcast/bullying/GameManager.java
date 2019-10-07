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
    public Lobby tryJoinLobby(Player player, String code){
        for (Lobby lobby: this.lobbies) {
            if(lobby.getCode().equals(code)){
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
        if(lobby.getMaxPlayers() >= lobby.getQueued().size()){
            return;
        }

        lobby.getQueued().put(player, false);
    }

    @Override
    public void removePlayer(Lobby lobby, Player player) {
        lobby.getQueued().remove(player);
    }

    @Override
    public boolean playerReadyUp(Lobby lobby, Player player){
        lobby.getQueued().put(player, true);
        return lobby.getQueued().get(player);
    }

    @Override
    public void startGame(Lobby lobby) {
        for (boolean isReady : lobby.getQueued().values()) {
            if(!isReady){
                return;
            }
        }

        this.games.add(new Game(new ArrayList<>(lobby.getQueued().keySet())));
        lobbies.remove(lobby);
    }
}
