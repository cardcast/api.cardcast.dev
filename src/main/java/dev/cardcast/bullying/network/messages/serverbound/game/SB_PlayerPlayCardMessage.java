package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

public class SB_PlayerPlayCardMessage extends ServerBoundWSMessage {

    private Card card;

    @Override
    public Event getEvent() {
        return new PlayerPlayCardEvent(this.getTrackingId(), this.card);
    }
}
