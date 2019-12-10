package dev.cardcast.bullying.network.messages.clientbound.lobby;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CB_UserCreatedGameMessage extends ClientBoundWSMessage {

    public CB_UserCreatedGameMessage(int trackingId, Lobby lobby) {
        this.setTrackingId(trackingId);
        this.lobby = lobby;
    }

    @Getter
    private final Lobby lobby;
}