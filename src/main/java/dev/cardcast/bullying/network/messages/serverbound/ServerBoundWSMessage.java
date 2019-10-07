package dev.cardcast.bullying.network.messages.serverbound;

import dev.cardcast.bullying.network.messages.WSMessage;

public class ServerBoundWSMessage extends WSMessage {

    public ServerBoundWSMessage() {
        super();
    }

    public ServerBoundWSMessage(int trackingId) {
        super(trackingId);
    }

}
