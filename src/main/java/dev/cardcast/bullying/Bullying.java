package dev.cardcast.bullying;

import dev.cardcast.bullying.network.handlers.GameServer;
import dev.cardcast.bullying.network.NetworkManager;
import lombok.Getter;

import java.io.IOException;
import java.util.logging.Logger;

public class Bullying {

    @Getter
    private static final Logger logger = Logger.getLogger("BULLYING");

    @Getter
    private GameServer server;

    @Getter
    private static final NetworkManager networkManager = new NetworkManager();

    Bullying(String[] args) {
        try {
            this.server = new GameServer(42069);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
