package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.BullyingGameLogic;
import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.IGameLogic;
import dev.cardcast.bullying.IGameManager;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.host.HostStartGameEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerCreateGameEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerJoinEvent;
import dev.cardcast.bullying.network.messages.clientbound.game.CB_HostCreatedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.game.CB_PlayerJoinMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.Session;

public class GameListener implements EventListener {

    private final IGameManager gameManagerLogic = new GameManager();
    private final IGameLogic gameLogic = new BullyingGameLogic();

    @EventHandler
    public void readyUp(Session session, PlayerJoinEvent event) {
        Lobby lobby = gameManagerLogic.tryJoinLobby(new Player(session, event.getName()), event.getToken());
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerJoinMessage(event.getTrackingId(), lobby)));
    }

    @EventHandler
    public void startGame(Session session, HostStartGameEvent event) {

    }

    @EventHandler
    public void playCard(Session session, PlayerPlayCardEvent event) {
        this.gameLogic.playCard(null, null, event.getCard());
    }

    @EventHandler
    public void createGame(Session session, PlayerCreateGameEvent event) {
        Lobby lobby = gameManagerLogic.createLobby(event.isPublic(), 7);
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_HostCreatedGameMessage(event.getTrackingId(), lobby)));
    }
}
