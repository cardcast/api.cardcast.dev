package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.util.Utils;
import jdk.nashorn.internal.parser.JSONParser;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/game")
public class GameConnector {

    private static final List<Session> SESSIONS = new ArrayList<>();

    private static final Map<Game, List<Session>> GAME_SESSIONS = new HashMap<>();

    @OnOpen
    public void onConnect(Session session) {
        Bullying.getLogger().info("New connection: " + session.getId());
        SESSIONS.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        this.handleMessage(session, message);
    }

    private void handleMessage(Session session, String message) {
        ServerBoundWSMessage wbMessage = null;

        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonMessage = (JsonObject) parser.parse(message);
            Class<? extends ServerBoundWSMessage> messageType = NetworkService.getMessageType(jsonMessage);
            if (messageType == null) {
                Bullying.getLogger().warning("UNKNOWN MESSAGE TYPE FOUND");
                return;
            }
            wbMessage = Utils.GSON.fromJson(message, messageType);
            Bullying.getNetworkService().handleEvent(wbMessage);
        } catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket ERROR: cannot parse Json message " + message);
        }
    }
}
