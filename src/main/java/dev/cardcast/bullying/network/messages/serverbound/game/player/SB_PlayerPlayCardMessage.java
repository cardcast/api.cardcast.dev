package dev.cardcast.bullying.network.messages.serverbound.game.player;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.player.PlayerPlayCardEvent;
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
