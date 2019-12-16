package dev.cardcast.bullying.network.messages.clientbound.client;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

import java.util.ArrayList;

public class CB_HostStartGameMessage extends ClientBoundWSMessage {


    @Getter
    private final ArrayList<Card> cards;

    @Getter
    private final boolean yourTurn;

    public CB_HostStartGameMessage(ArrayList<Card> cards, boolean yourTurn) {
        this.cards = cards;
        this.yourTurn = yourTurn;
    }


}