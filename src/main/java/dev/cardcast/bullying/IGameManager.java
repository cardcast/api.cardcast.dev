package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;

import javax.websocket.Session;
import java.util.List;

public interface IGameManager {
    List<Lobby> getLobbies();

    List<Game> getGames();

    Lobby tryJoinLobby(Player player, String code);

    Lobby createLobby(boolean isPublic, int maxPlayers, Host host);

    void addPlayer(Lobby lobby, Player player);

    void removePlayer(Lobby lobby, Player player);

    Game startGame(Lobby lobby);

    Game findPlayer(Player player);
}
