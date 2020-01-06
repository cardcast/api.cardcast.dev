package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.*;

import javax.websocket.Session;
import java.util.List;

public interface IGameManager {

    Lobby tryJoinLobby(Player player, String code);

    Lobby createLobby(boolean isPublic, int maxPlayers, Host host);

    void addPlayer(Lobby lobby, Player player);

    void removePlayer(Lobby lobby, Player player);

    Game startGame(Lobby lobby);

    PlayerContainer findPlayer(Player player);
}
