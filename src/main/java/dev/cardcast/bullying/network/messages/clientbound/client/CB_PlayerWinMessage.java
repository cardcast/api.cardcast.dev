package dev.cardcast.bullying.network.messages.clientbound.client;

import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

public class CB_PlayerWinMessage extends ClientBoundWSMessage {

    @Getter
    private boolean win;

    public CB_PlayerWinMessage(int trackingId) {
        this.setTrackingId(trackingId);
        this.win = true;
    }
}

