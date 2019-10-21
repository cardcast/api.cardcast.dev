package dev.cardcast.bullying.network.events.types;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PlayerPlayCardEvent extends Event {

    @Getter
    private Card card;

    public PlayerPlayCardEvent(String cardString) {
        this(Card.getCard(cardString));
    }

    public PlayerPlayCardEvent(Card card) {
        this.card = card;
    }
}
