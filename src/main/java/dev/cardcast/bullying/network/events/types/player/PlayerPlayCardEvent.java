package dev.cardcast.bullying.network.events.types.player;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PlayerPlayCardEvent extends Event {

    @Getter
    private final int trackingId;

    @Getter
    private Card card;

    public PlayerPlayCardEvent(int trackingId, String cardString) {
        this(trackingId, Card.getCard(cardString));
    }

    public PlayerPlayCardEvent(int trackingId, Card card) {
        this.card = card;
        this.trackingId = trackingId;
    }
}
