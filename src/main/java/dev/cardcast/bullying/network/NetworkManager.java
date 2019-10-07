package dev.cardcast.bullying.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.handlers.ServerThread;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

    @Getter
    public GatewayManager gateway = new GatewayManager(this);

    @Getter
    private final Map<Player, ServerThread> clients;

    public NetworkManager() {
        this.clients = new HashMap<>();
        this.gateway.registerMessageTypes(

        );
    }

    private static final Gson GSON = new GsonBuilder().create();

    private static final JsonParser JSON_PARSER = new JsonParser();

    public ServerBoundWSMessage parsePayload(String jsonMessage) {
        JsonObject messageObj = JSON_PARSER.parse(jsonMessage).getAsJsonObject();

        String type = messageObj.get("type").getAsString();
        Class<? extends ServerBoundWSMessage> typeClass = Bullying.getNetworkManager().getGateway().resolveType(type);
        if (typeClass == null) {
            return null;
        }

        return GSON.fromJson(messageObj, typeClass);
    }

    public void addClient(Player player, ServerThread serverThread) {
        this.clients.put(player, serverThread);
    }
}
