package dev.cardcast.bullying.network.messages.serverbound;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.messages.WSMessage;

public abstract class ServerBoundWSMessage extends WSMessage {

    public ServerBoundWSMessage() {
        super();
    }

    public ServerBoundWSMessage(int trackingId) {
        super(trackingId);
    }

    public abstract Event getEvent();

}
