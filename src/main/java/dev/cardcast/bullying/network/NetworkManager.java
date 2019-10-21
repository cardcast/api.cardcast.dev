package dev.cardcast.bullying.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import lombok.Getter;

public class NetworkManager {

    @Getter
    public GatewayManager gateway = new GatewayManager(this);

    public NetworkManager() {
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


}
