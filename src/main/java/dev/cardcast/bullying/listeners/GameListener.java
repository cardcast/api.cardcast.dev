package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.BullyingGameLogic;
import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.IGameLogic;
import dev.cardcast.bullying.IGameManager;
import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.host.HostStartGameEvent;
import dev.cardcast.bullying.network.events.types.lobby.UserCreateGameEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerDrawCardEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerJoinEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.messages.clientbound.client.*;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_PlayerJoinedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_PlayerPlayedCardMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_StartedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.lobby.CB_UserCreatedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.lobby.CB_UserJoinedGameMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameListener implements EventListener {

    private GameManager gameManager = GameManager.getInstance();

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

        for (Lobby lobby : this.gameManager.getLobbies()) {
            if (lobby.getCode().equals(event.getToken())) {
                selectedLobby = lobby;
                break;
            }
        }

        if (selectedLobby.addPlayer(player)) {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_UserJoinedGameMessage(event.getTrackingId(), selectedLobby, player.getUuid())));
            selectedLobby.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerJoinedGameMessage(event.getTrackingId(), player)));
        }
    }

    @EventHandler
    public void startGame(Session session, HostStartGameEvent event) {
        Lobby startingLobby = null;

        for (Lobby lobby : this.gameManager.getLobbies()) {
            if (lobby.getHost().getSession().equals(session)) {
                startingLobby = lobby;
            }
        }

        if (startingLobby != null) {
            Game game = this.gameManager.startGame(startingLobby);

            List<Player> queued = startingLobby.getQueued();

            for (Player player : queued) {
                boolean turn = false;
                if (game.isTheirTurn(player)) {
                    turn = true;
                }

                player.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_HostStartGameMessage(player.getHand().getCards(), turn)));
            }
            startingLobby.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_StartedGameMessage(queued.get(0), game.getStack(), event.getTrackingId())));
        }

    }

    @EventHandler
    public void playCard(Session session, PlayerPlayCardEvent event) {
        Game game = this.gameManager.getGames().stream().filter(filterGame -> {
            return filterGame.getPlayers().stream().anyMatch(player -> player.getSession().getId().equals(session.getId()));
        }).findFirst().get();
        Player player = game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getSession().getId().equals(session.getId())).findFirst().get();

        boolean wasPlayed = this.gameLogic.playCard(game, player, event.getCard());
        if (wasPlayed) {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerPlayedCardMessage(event.getTrackingId())));
            game.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerPlayedCardMessage(game.getPlayers().get(game.getTurnIndex()), event.getCard())));
            Player nextTurn = game.getPlayers().get(game.getTurnIndex());
            nextTurn.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayersTurnMessage()));
        } else {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerPlayCardErrorMessage(event.getTrackingId())));
        }
    }

    @EventHandler
    public void createGame(Session session, UserCreateGameEvent event) {
        Host host = new Host();
        host.setSession(session);
        Lobby lobby = this.gameManager.createLobby(event.isPublic(), 7, host);
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_UserCreatedGameMessage(event.getTrackingId(), lobby)));
    }

    @EventHandler
    public void drawCard(Session session, PlayerDrawCardEvent event) {
        Game game = this.gameManager.getGames().stream().filter(filterGame -> {
            return filterGame.getPlayers().stream().anyMatch(player -> player.getSession().getId().equals(session.getId()));
        }).findFirst().get();
        Player player = game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getSession().getId().equals(session.getId())).findFirst().get();

        List<Card> cards = gameLogic.drawCard(game, player);

        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerDrawCardsMessage(cards, event.getTrackingId())));
        this.gameLogic.endTurn(game, player);
        Player nextTurn = game.getPlayers().get(game.getTurnIndex());
        nextTurn.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayersTurnMessage()));
    }
}
