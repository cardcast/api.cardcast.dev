package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.BullyingGameLogic;
import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.IGameLogic;
import dev.cardcast.bullying.entities.*;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.NetworkService;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.host.HostStartGameEvent;
import dev.cardcast.bullying.network.events.types.lobby.UserCreateGameEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerDrawCardEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerJoinEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerPassTurnEvent;
import dev.cardcast.bullying.network.events.types.player.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.messages.clientbound.client.*;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_PlayerJoinedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_PlayerPlayedCardMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_PlayerWinMessage;
import dev.cardcast.bullying.network.messages.clientbound.host.HB_StartedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.lobby.CB_UserCreatedGameMessage;
import dev.cardcast.bullying.network.messages.clientbound.lobby.CB_UserJoinedGameMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class GameListener implements EventListener {

    private GameManager gameManager = GameManager.getInstance();
    private NetworkService networkService = NetworkService.INSTANCE;

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
        Device device = this.networkService.getDeviceBySession(session);
        Player player;
        if (device instanceof Host) {
            player = new Player(device.getUuid(), session);

            this.networkService.getDevices().remove(device);
            this.networkService.getDevices().add(player);
        } else {
            player = (Player) this.networkService.getDeviceBySession(session);
        }

        player.setName(event.getName());


        for (PlayerContainer container : this.gameManager.getContainers()) {
            if (container instanceof Lobby) {
                Lobby lobby = ((Lobby) container);
                if (lobby.getCode().equals(event.getToken())) {
                    if (lobby.addPlayer(player)) {
                        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_UserJoinedGameMessage(event.getTrackingId(), lobby, player.getUuid())));
                        lobby.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerJoinedGameMessage(event.getTrackingId(), player)));
                    }
                    break;
                }
            }
        }


    }


    @EventHandler
    public void startGame(Session session, HostStartGameEvent event) {
        Lobby startingLobby = null;

        for (PlayerContainer container : this.gameManager.getContainers()) {
            if (container instanceof Lobby) {
                Lobby lobby = (Lobby) container;
                if (lobby.getHost().getSession().equals(session)) {
                    startingLobby = lobby;
                }
            }
        }

        if (startingLobby != null) {
            Game game = this.gameManager.startGame(startingLobby);

            List<Player> queued = startingLobby.getPlayers();

            for (Player player : queued) {
                player = (Player) this.networkService.getDeviceByUuid(player.getUuid());
                boolean turn = false;
                if (game.isTheirTurn(player)) {
                    turn = true;
                }

                player.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_HostStartGameMessage(player.getHand().getCards(), turn)));
            }
            startingLobby.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_StartedGameMessage(game.getPlayers().get(0), game.getStack(), event.getTrackingId())));
        }

    }

    @EventHandler
    public void playCard(Session session, PlayerPlayCardEvent event) {
        Player player = (Player) this.networkService.getDeviceBySession(session);
        Game game = (Game) this.gameManager.findPlayer(player);

        boolean wasPlayed = this.gameLogic.playCard(game, player, event.getCard());
        boolean playerWon = this.gameLogic.playerWon(game, player);

        if (wasPlayed) {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerPlayedCardMessage(event.getTrackingId())));

            Host host = (Host) this.networkService.getDeviceByUuid(game.getHost().getUuid());
            host.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerPlayedCardMessage(game.getPlayers().get(game.getTurnIndex()), event.getCard())));

            Player nextTurn = game.getPlayers().get(game.getTurnIndex());
            this.networkService.getDeviceByUuid(nextTurn.getUuid()).getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayersTurnMessage()));
        }
        if (playerWon) {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerWinMessage(event.getTrackingId())));

            Host host = (Host) this.networkService.getDeviceByUuid(game.getHost().getUuid());
            host.getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerWinMessage(event.getTrackingId(), player)));
        } else {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerPlayCardErrorMessage(event.getTrackingId())));

        }
    }

    @EventHandler
    public void createGame(Session session, UserCreateGameEvent event) {
        Device device = this.networkService.getDeviceBySession(session);
        Host host = new Host(device.getSession(), device.getUuid());

        this.networkService.getDevices().remove(device);
        this.networkService.getDevices().add(host);


        Lobby lobby = this.gameManager.createLobby(event.isPublic(), 7, host);
        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_UserCreatedGameMessage(event.getTrackingId(), lobby)));
    }

    @EventHandler
    public void drawCard(Session session, PlayerDrawCardEvent event) {
        Player player = (Player) this.networkService.getDeviceBySession(session);
        Game game = (Game) this.gameManager.findPlayer(player);

        List<Card> cards = gameLogic.drawCard(game, player);

        session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerDrawCardsMessage(cards, event.getTrackingId())));


        Player nextTurn = game.getPlayers().get(game.getTurnIndex());
        this.networkService.getDeviceByUuid(nextTurn.getUuid()).getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayersTurnMessage()));
        game.getHost().getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerPlayedCardMessage(nextTurn, null)));

    }

    @EventHandler
    public void passTurn(Session session, PlayerPassTurnEvent event) {
        Player player = (Player) this.networkService.getDeviceBySession(session);
        Game game = (Game) this.gameManager.findPlayer(player);

        if (gameLogic.endTurn(game, player)) {
            session.getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayerDrawCardsMessage(new ArrayList<>(), event.getTrackingId())));
            Player nextTurn = game.getPlayers().get(game.getTurnIndex());

            this.networkService.getDeviceByUuid(game.getHost().getUuid()).getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new HB_PlayerPlayedCardMessage(nextTurn, null)))
            this.networkService.getDeviceByUuid(nextTurn.getUuid()).getSession().getAsyncRemote().sendText(Utils.GSON.toJson(new CB_PlayersTurnMessage()));
        }
    }
}
