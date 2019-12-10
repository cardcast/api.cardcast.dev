package dev.cardcast.bullying.network.messages.serverbound.game.lobby;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.lobby.UserCreateGameEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_UserCreateGameMessage extends ServerBoundWSMessage {

    private boolean publik;

    @Override
    public Event getEvent() {
        return new UserCreateGameEvent(this.getTrackingId(), this.publik);
    }
}
