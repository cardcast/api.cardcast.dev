package dev.cardcast.bullying.network;

import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

import java.util.Observer;

public interface Listener {

    void onMessage(ServerBoundWSMessage message);
}
