package dev.cardcast.bullying.network.messages.clientbound.host;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HB_PlayerJoinedGameMessage extends ClientBoundWSMessage {

    public HB_PlayerJoinedGameMessage(int trackingId, Player lobby) {
        this.setTrackingId(trackingId);
        this.player = lobby;
    }

    @Getter
    private final Player player;
}
