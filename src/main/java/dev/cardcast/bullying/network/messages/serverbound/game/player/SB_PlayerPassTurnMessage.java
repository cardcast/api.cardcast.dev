package dev.cardcast.bullying.network.messages.serverbound.game.player;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.player.PlayerPassTurnEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerPassTurnMessage extends ServerBoundWSMessage {

    @Override
    public Event getEvent() {
        return new PlayerPassTurnEvent(this.getTrackingId());
    }
}

