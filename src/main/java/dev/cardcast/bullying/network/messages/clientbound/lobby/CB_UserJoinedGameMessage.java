package dev.cardcast.bullying.network.messages.clientbound.lobby;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

import java.util.UUID;

public class CB_UserJoinedGameMessage extends ClientBoundWSMessage {


    public CB_UserJoinedGameMessage(int trackingId, Lobby lobby, UUID uuid) {
        this.setTrackingId(trackingId);
        this.uuid = uuid;
        this.lobby = lobby;
        this.lobby.getPlayers().clear();
    }

    @Getter
    private final UUID uuid;

    @Getter
    private final Lobby lobby;
}
