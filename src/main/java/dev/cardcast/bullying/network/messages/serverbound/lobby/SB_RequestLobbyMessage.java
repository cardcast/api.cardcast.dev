package dev.cardcast.bullying.network.messages.serverbound.lobby;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.LobbyRequestEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_RequestLobbyMessage extends ServerBoundWSMessage {

    private String code;

    @Override
    public Event getEvent() {
        return new LobbyRequestEvent(this.code);
    }
}
