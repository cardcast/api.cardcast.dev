package dev.cardcast.bullying.network.messages.clientbound.client;

import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

public class CB_PlayerPlayCardErrorMessage extends ClientBoundWSMessage {

    @Getter
    private boolean error;

    public CB_PlayerPlayCardErrorMessage(int trackingId) {
        this.setTrackingId(trackingId);
        this.error = true;
    }
}
