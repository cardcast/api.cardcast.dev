package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.IGameManager;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.HostStartGameEvent;
import dev.cardcast.bullying.network.events.types.PlayerCreateGameEvent;
import dev.cardcast.bullying.network.events.types.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import dev.cardcast.bullying.network.messages.clientbound.game.CB_PlayerCreatedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.game.CB_PlayerReadyedUpMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.Session;

public class GameListener implements EventListener {

    private final IGameManager gameManagerLogic = new GameManager();

    @EventHandler
    public void readyUp(Session session, PlayerReadyUpEvent event) {
        Lobby lobby = gameManagerLogic.tryJoinLobby(new Player(session, event.getName()), event.getToken());
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerReadyedUpMessage(event.getTrackingId(), lobby)));
    }

    @EventHandler
    public void startGame(Session session, HostStartGameEvent event) {

    }

    @EventHandler
    public void playCard(Session session, PlayerPlayCardEvent event) {

    }

    @EventHandler
    public void createGame(Session session, PlayerCreateGameEvent event) {
        Lobby lobby = gameManagerLogic.createLobby(event.isPublic(), 7);
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerCreatedGameMessage(event.getTrackingId(), lobby)));
    }
}
