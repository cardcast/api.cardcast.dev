package dev.cardcast.bullying.network.messages.clientbound.client;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

import java.util.List;

public class CB_PlayerDrawCardsMessage extends ClientBoundWSMessage {

    @Getter
    private final List<Card> cards;

    public CB_PlayerDrawCardsMessage(List<Card> cards, int trackingId) {
        this.cards = cards;
        this.setTrackingId(trackingId);
    }
}
