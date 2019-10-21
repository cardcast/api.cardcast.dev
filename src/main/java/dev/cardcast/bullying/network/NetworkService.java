package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import com.sun.security.ntlm.Server;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.network.messages.serverbound.lobby.SB_RequestLobbyMessage;

import java.util.ArrayList;
import java.util.List;

public class NetworkService {

    static {
        NetworkService.messages.add(SB_RequestLobbyMessage.class);
    }

    private static List<Class<? extends ServerBoundWSMessage>> messages = new ArrayList<>();

    public static Class<? extends ServerBoundWSMessage> getMessageType(JsonObject json) {
        String type = json.get("type").getAsString();
        for (Class<? extends ServerBoundWSMessage> messageType : messages) {
            if (messageType.getSimpleName().equals(type)) {
                return messageType;
            }
        }
        return null;

    }
}
