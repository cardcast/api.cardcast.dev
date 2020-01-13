package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.entities.Device;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.util.Utils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.UUID;

@ServerEndpoint(value = "/game")
public class GameConnector {

    @OnOpen
    public void onConnect(Session session) {
        Bullying.getLogger().info("New connection: " + session.getId());
        if (session.getRequestURI().getQuery() != null) {
            UUID uuid = UUID.fromString(session.getRequestURI().getQuery());
            Device device = NetworkService.INSTANCE.getDeviceByUuid(uuid);
            if (device != null) {
                device.setSession(session);
                Bullying.getLogger().info("Old device reconnected, UUID: " + uuid);
            }
        }

        UUID uuid = UUID.randomUUID();
        NetworkService.INSTANCE.registerPlayer(uuid, session);
        session.getAsyncRemote().sendText(uuid.toString());
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

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
//        Player player = GameManager.getInstance().getPlayer(session);
//
//        System.out.println("ERROR IN SESSION: " + session.getId() + " for player: " + player.getName());
//        throwable.printStackTrace();
    }

}