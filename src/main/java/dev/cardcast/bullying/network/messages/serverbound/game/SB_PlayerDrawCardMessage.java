package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerDrawCardEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerDrawCardMessage extends ServerBoundWSMessage {

    @Override
    public Event getEvent() {
        return new PlayerDrawCardEvent(this.getTrackingId());
    }
}
