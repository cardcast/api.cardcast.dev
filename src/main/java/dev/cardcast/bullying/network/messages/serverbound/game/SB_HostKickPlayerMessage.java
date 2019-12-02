package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.HostKickPlayerEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_HostKickPlayerMessage extends ServerBoundWSMessage {

    private String playerName;

    @Override
    public Event getEvent() {
        return new HostKickPlayerEvent(this.getTrackingId(), this.playerName);
    }
}
