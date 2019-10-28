package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.interfaces.IGameManagerLogic;
import dev.cardcast.bullying.network.GameConnector;
import dev.cardcast.bullying.network.NetworkService;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;

import javax.websocket.Session;

public class GameListener implements EventListener {

    private final IGameManagerLogic gameManagerLogic = new GameManager();

    @EventHandler
    public void readyUp(Session session, PlayerReadyUpEvent event) {
        gameManagerLogic.tryJoinLobby(new Player(session, event.getName()), event.getToken());
    }

    @EventHandler
    public void playCard(Session session) {

    }
}
