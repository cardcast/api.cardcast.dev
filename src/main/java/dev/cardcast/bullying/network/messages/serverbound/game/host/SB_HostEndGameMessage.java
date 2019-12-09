package dev.cardcast.bullying.network.messages.serverbound.game.host;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.host.HostEndsGameEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_HostEndGameMessage extends ServerBoundWSMessage {

    @Override
    public Event getEvent() {
        return new HostEndsGameEvent();
    }
}
