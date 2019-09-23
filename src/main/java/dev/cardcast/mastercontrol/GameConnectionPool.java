package dev.cardcast.mastercontrol;

import dev.cardcast.mastercontrol.managers.game.Game;
import dev.cardcast.mastercontrol.managers.game.GameServerConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameConnectionPool {

    private static HashMap<String, GameServerConnector> connectors = new HashMap<>();

    public static GameServerConnector findGameConnectorByName(String gameName) {
        return connectors.get(gameName);
    }

    public static void addGame(Game game) {
        connectors.put(game.getName(), new GameServerConnector(game));
    }
}
