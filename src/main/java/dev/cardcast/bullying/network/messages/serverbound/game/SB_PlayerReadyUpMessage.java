package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerReadyUpMessage extends ServerBoundWSMessage {

    private boolean ready;

    public SB_PlayerReadyUpMessage(boolean ready) {
        this.ready = ready;
    }

    @Override
    public Event getEvent() {
        return new PlayerReadyUpEvent(this.ready);
    }
}
