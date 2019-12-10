package dev.cardcast.bullying.network.messages.clientbound.lobby;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

public class CB_UserJoinedGameMessage extends ClientBoundWSMessage {

    public CB_UserJoinedGameMessage(int trackingId, Lobby lobby) {
        this.setTrackingId(trackingId);
        this.lobby = lobby;
    }

    @Getter
    private final Lobby lobby;
}
