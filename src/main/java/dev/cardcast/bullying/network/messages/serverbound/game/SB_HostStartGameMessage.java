package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.HostStartGameEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_HostStartGameMessage extends ServerBoundWSMessage {

    @Override
    public Event getEvent() {
        return new HostStartGameEvent();
    }
}
