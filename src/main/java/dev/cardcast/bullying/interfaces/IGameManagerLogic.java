package dev.cardcast.bullying.interfaces;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;

public interface IGameManagerLogic {
    Lobby findLobbyByCode(String code);

    Lobby createLobby(boolean isPublic, int maxPlayers);

    void addPlayer(Lobby lobby, Player player);

    void removePlayer(Lobby lobby, Player player);

    void startGame(Lobby lobby);
}
