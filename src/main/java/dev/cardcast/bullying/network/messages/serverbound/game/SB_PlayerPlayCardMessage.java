package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerPlayCardEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import lombok.Getter;

public class SB_PlayerPlayCardMessage extends ServerBoundWSMessage {

    @Getter
    private String card;

    @Override
    public Event getEvent() {
        return new PlayerPlayCardEvent(this.getTrackingId(), this.card);
    }
}
