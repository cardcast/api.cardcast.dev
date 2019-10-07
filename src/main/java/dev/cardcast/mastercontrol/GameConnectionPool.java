package dev.cardcast.mastercontrol;

import dev.cardcast.mastercontrol.managers.game.Game;
import dev.cardcast.mastercontrol.managers.game.GameServerConnector;
import lombok.Getter;

import java.util.HashMap;
import java.util.logging.Logger;

public class GameConnectionPool {

    @Getter
    private static Logger logger = Logger.getLogger("GMC");

    private static HashMap<String, GameServerConnector> connectors = new HashMap<>();

    public static GameServerConnector findGameConnectorByName(String gameName) {
        return connectors.get(gameName);
    }

    public static void addGame(Game game) {
        GameConnectionPool.getLogger().info("Registered game: " + game.getName());
        connectors.put(game.getName(), new GameServerConnector(game));
    }
}
