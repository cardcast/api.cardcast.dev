package dev.cardcast.bullying.interfaces;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.LobbySettings;
import dev.cardcast.bullying.entities.Player;

public interface ILobbyLogic {

    Lobby findLobbyByCode(String code);

    Lobby createLobby(LobbySettings settings);

    void addPlayer(Lobby lobby, Player player);

    void removePlayer(Lobby lobby, Player player);

    void startGame(Lobby lobby);
}
