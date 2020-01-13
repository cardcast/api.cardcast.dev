package dev.cardcast.bullying.network.messages.clientbound.client;

import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;

public class CB_PlayerPassedTurn extends ClientBoundWSMessage {

    public CB_PlayerPassedTurn(int trackingId) {
        this.setTrackingId(trackingId);
    }

}
