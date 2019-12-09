package dev.cardcast.bullying.network.messages.serverbound.game.host;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.player.PlayerCreateGameEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_HostCreateGameMessage extends ServerBoundWSMessage {

    private boolean publik;

    @Override
    public Event getEvent() {
        return new PlayerCreateGameEvent(this.getTrackingId(), this.publik);
    }
}
