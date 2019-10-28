package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.IGameManager;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.HostStartGameEvent;
import dev.cardcast.bullying.network.events.types.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;

import javax.websocket.Session;

public class GameListener implements EventListener {

    private final IGameManager gameManagerLogic = new GameManager();

    @EventHandler
    public void readyUp(Session session, PlayerReadyUpEvent event) {
        gameManagerLogic.tryJoinLobby(new Player(session, event.getName()), event.getToken());
    }

    @EventHandler
    public void startGame(Session session, HostStartGameEvent event) {

    }

    @EventHandler
    public void playCard(Session session, PlayerPlayCardEvent event) {

    }
}
