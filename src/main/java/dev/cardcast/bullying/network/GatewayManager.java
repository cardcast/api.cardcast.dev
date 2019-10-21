package dev.cardcast.bullying.network;

import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GatewayManager {

    private final Map<String, Class<? extends ServerBoundWSMessage>> packetTypes;
    private final NetworkManager manager;
    private ArrayList<Listener> listeners = new ArrayList<>();


    GatewayManager(NetworkManager manager) {
        super();

        this.packetTypes = new HashMap<>();
        this.manager = manager;
    }

    void registerMessageType(Class<? extends ServerBoundWSMessage> clazz) {
        String type = clazz.getSimpleName();
        if (type.endsWith("Message")) {
            type = type.substring(0, type.length() - 7); // Remove "Message" from the end of the clazz name.
        }
        this.packetTypes.put(type, clazz);
    }

    @SafeVarargs
    final void registerMessageTypes(Class<? extends ServerBoundWSMessage>... classes) {
        for (Class<? extends ServerBoundWSMessage> clazz : classes) {
            this.registerMessageType(clazz);
        }
    }

    public Class<? extends ServerBoundWSMessage> resolveType(String type) {
        return this.packetTypes.get(type);
    }

    void sendMessage(ClientBoundWSMessage message) {

    }

    public void onMessage(ServerBoundWSMessage packet) {
        this.listeners.forEach(listener -> onMessage(packet));
    }


}