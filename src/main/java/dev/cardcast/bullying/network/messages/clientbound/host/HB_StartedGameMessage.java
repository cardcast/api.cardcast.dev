package dev.cardcast.bullying.network.messages.clientbound.host;


import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

import java.util.ArrayList;

public class HB_StartedGameMessage extends ClientBoundWSMessage {

    @Getter
    public Player firstTurn;

    @Getter
    public ArrayList<Card> stack;

    public HB_StartedGameMessage(Player firstTurn, ArrayList<Card> stack, int trackingId) {
        this.firstTurn = firstTurn;
        this.stack = stack;
        this.setTrackingId(trackingId);
    }
}
