package dev.cardcast.bullying.network.messages.clientbound.game;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CB_HostCreatedGameMessage extends ClientBoundWSMessage {

    public CB_HostCreatedGameMessage(int trackingId, Lobby lobby) {
        this.setTrackingId(trackingId);
        this.lobby = lobby;
    }

    @Getter
    private final Lobby lobby;
}