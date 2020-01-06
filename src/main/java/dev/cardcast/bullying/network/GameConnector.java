package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/game")
public class GameConnector {

    @OnOpen
    public void onConnect(Session session) {
        Bullying.getLogger().info("New connection: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        JsonParser parser = new JsonParser();
        JsonObject jsonMessage = (JsonObject) parser.parse(message);
        Class<? extends ServerBoundWSMessage> messageType = NetworkService.getMessageEvent(jsonMessage);
        if (messageType == null) {
            Bullying.getLogger().warning("UNKNOWN MESSAGE TYPE FOUND");
            return;
        }

        try {
            ServerBoundWSMessage wbMessage = Utils.GSON.fromJson(message, messageType);
            NetworkService.INSTANCE.handleEvent(session, wbMessage);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.out.println(message);
        }
    }

    @OnClose
    public void onDisconnect(Session session) {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("ERROR IN SESSION: "  + session.getId());
        throwable.printStackTrace();
    }

}