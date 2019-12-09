package dev.cardcast.bullying.network.messages.serverbound.game.host;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.host.HostStartGameEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_HostStartGameMessage extends ServerBoundWSMessage {

    @Override
    public Event getEvent() {
        return new HostStartGameEvent(this.getTrackingId());
    }
}
