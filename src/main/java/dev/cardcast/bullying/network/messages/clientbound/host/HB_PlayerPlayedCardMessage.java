package dev.cardcast.bullying.network.messages.clientbound.host;

import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HB_PlayerPlayedCardMessage extends ClientBoundWSMessage {

    @Getter
    private Player nextPlayer;

    @Getter
    private Card card;

}