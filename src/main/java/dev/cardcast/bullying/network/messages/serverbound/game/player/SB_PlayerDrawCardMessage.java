package dev.cardcast.bullying.network.messages.serverbound.game.player;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.player.PlayerDrawCardEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerDrawCardMessage extends ServerBoundWSMessage {

    @Override
    public Event getEvent() {
        return new PlayerDrawCardEvent(this.getTrackingId());
    }
}
