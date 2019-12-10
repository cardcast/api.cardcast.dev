package dev.cardcast.bullying.network.messages.serverbound.game.player;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.player.PlayerJoinEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerJoinMessage extends ServerBoundWSMessage {

    private String name;

    private String token;

    public SB_PlayerJoinMessage(String name, String token) {
        this.name = name;
        this.token = token;
    }

    @Override
    public Event getEvent() {
        return new PlayerJoinEvent(this.getTrackingId(), this.name, this.token);
    }
}
