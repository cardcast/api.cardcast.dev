package dev.cardcast.bullying.network.messages.clientbound.host;

import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HB_PlayerReadyUpMessage extends ClientBoundWSMessage {
    public HB_PlayerReadyUpMessage(int trackingId, Player playerWhoReadiedUp) {
        this.setTrackingId(trackingId);
        this.player = playerWhoReadiedUp;
    }

    @Getter
    private final Player player;
}
