package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerPlayCardMessage extends ServerBoundWSMessage {

    private String card;

    @Override
    public Event getEvent() {
        return new PlayerPlayCardEvent(this.card);
    }
}