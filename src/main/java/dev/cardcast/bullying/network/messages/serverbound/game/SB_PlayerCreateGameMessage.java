package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerCreateGameEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerCreateGameMessage extends ServerBoundWSMessage {

    private boolean publik;

    @Override
    public Event getEvent() {
        return new PlayerCreateGameEvent(this.getTrackingId(), this.publik);
    }
}
