package dev.cardcast.bullying.interfaces;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;

public interface IGameManagerLogic {
    Lobby tryJoinLobby(Player player, String code);

    Lobby createLobby(boolean isPublic, int maxPlayers);

    void addPlayer(Lobby lobby, Player player);

    void removePlayer(Lobby lobby, Player player);

    boolean playerReadyUp(Lobby lobby, Player player);

    Game startGame(Lobby lobby);
}
