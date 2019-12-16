package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.BullyingGameLogic;
import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.IGameLogic;
import dev.cardcast.bullying.IGameManager;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.host.HostStartGameEvent;
import dev.cardcast.bullying.network.events.types.lobby.UserCreateGameEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerJoinEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.messages.clientbound.client.CB_HostStartGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_PlayerJoinedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_StartedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.lobby.CB_UserCreatedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.lobby.CB_UserJoinedGameMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class GameListener implements EventListener {

    private final IGameManager gameManagerLogic = new GameManager();
    private final IGameLogic gameLogic = new BullyingGameLogic();


    /**
     * Informs the host of the lobby that the player who calls this
     * message has readied up.
     *
     * @param session session given by client
     * @param event   event called by client
     */

    @EventHandler
    public void joinGame(Session session, PlayerJoinEvent event) {
        Lobby selectedLobby = null;
        Player player = new Player(session, event.getName());

        for (Lobby lobby : gameManagerLogic.getLobbies()) {
            if (lobby.getCode().equals(event.getToken())) {
                selectedLobby = lobby;
                break;
            }
        }

        if (selectedLobby.addPlayer(player)) {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_UserJoinedGameMessage(event.getTrackingId(), selectedLobby)));
            selectedLobby.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerJoinedGameMessage(event.getTrackingId(), player)));
        }
    }

    @EventHandler
    public void startGame(Session session, HostStartGameEvent event) {
        Lobby startingLobby = null;

        for (Lobby lobby : gameManagerLogic.getLobbies()) {
            if (lobby.getHost().getSession().equals(session)) {
                startingLobby = lobby;
            }
        }

        if (startingLobby != null) {
            gameManagerLogic.startGame(startingLobby);

            List<Player> queued = startingLobby.getQueued();
            for (Player player : queued) {
                player.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_HostStartGameMessage(new ArrayList<>(), false)));
            }
            startingLobby.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_StartedGameMessage(queued.get(0), new ArrayList<>(), event.getTrackingId())));
        }

    }

    @EventHandler
    public void playCard(Session session, PlayerPlayCardEvent event) {
        this.gameLogic.playCard(null, null, event.getCard());
    }

    @EventHandler
    public void createGame(Session session, UserCreateGameEvent event) {
        Host host = new Host();
        host.setSession(session);
        Lobby lobby = gameManagerLogic.createLobby(event.isPublic(), 7, host);
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_UserCreatedGameMessage(event.getTrackingId(), lobby)));
    }
}
