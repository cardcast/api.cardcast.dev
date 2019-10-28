package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.util.Utils;
import lombok.Getter;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint(value = "/game/{token}")
public class GameConnector {

    private static final Map<String, Game> GAME_SESSIONS = new HashMap<>();

    public static Map<String, Game> getGameSessions() {
        return Collections.unmodifiableMap(GAME_SESSIONS);
    }

    @OnOpen
    public void onConnect(Session session, @PathParam("token") String token) {
        Bullying.getLogger().info("New connection: " + session.getId());

        if (GAME_SESSIONS.get(token) == null) {
            GAME_SESSIONS.put(token, new Game(token));
        }
        GAME_SESSIONS.get(token).getPlayers().add(new Player(session));
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        this.handleMessage(session, message);
    }

    private void handleMessage(Session session, String message) {
        ServerBoundWSMessage wbMessage;

        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonMessage = (JsonObject) parser.parse(message);
            Class<? extends ServerBoundWSMessage> messageType = NetworkService.getMessageEvent(jsonMessage);
            if (messageType == null) {
                Bullying.getLogger().warning("UNKNOWN MESSAGE TYPE FOUND");
                return;
            }
            wbMessage = Utils.GSON.fromJson(message, messageType);
            Bullying.getNetworkService().handleEvent(session, wbMessage);
        } catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket ERROR: cannot parse Json message " + message);
        }
    }
}
