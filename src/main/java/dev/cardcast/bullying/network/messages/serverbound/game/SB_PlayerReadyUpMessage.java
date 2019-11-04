package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerReadyUpMessage extends ServerBoundWSMessage {

    private String name;

    private String token;

    public SB_PlayerReadyUpMessage(String name, String token) {
        this.name = name;
        this.token = token;
    }

    @Override
    public Event getEvent() {
        return new PlayerReadyUpEvent(this.name, this.token);
    }
}
