package dev.cardcast.bullying.network.messages.clientbound.host;

import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

public class HB_PlayerPlayedCardMessage extends ClientBoundWSMessage {

    @Getter
    private Player player;

    @Getter
    private Card card;

}