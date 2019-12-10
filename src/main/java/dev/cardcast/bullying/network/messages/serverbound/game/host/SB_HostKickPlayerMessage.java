package dev.cardcast.bullying.network.messages.serverbound.game.host;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.host.HostKickPlayerEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_HostKickPlayerMessage extends ServerBoundWSMessage {

    private String playerName;

    @Override
    public Event getEvent() {
        return new HostKickPlayerEvent(this.getTrackingId(), this.playerName);
    }
}
