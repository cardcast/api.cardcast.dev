package dev.cardcast.mastercontrol.managers.game;

import lombok.Getter;

public class GameServerConnector extends ServerConnector {

    @Getter
    private Game game;

    public GameServerConnector(Game game) {
        super(game.getHost(), game.getPort());
        this.game = game;
    }

    @Override
    void onMessage(String message) {
        System.out.println(message);
    }
}
