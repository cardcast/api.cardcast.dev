package dev.cardcast.bullying.network.messages.clientbound.host;

import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

public class HB_PlayerWinMessage extends ClientBoundWSMessage {

    @Getter
    private boolean win;

    @Getter
    private Player winningPlayer;

    public HB_PlayerWinMessage(int trackingId, Player winningPlayer) {
        this.setTrackingId(trackingId);
        this.win = true;
        this.winningPlayer = winningPlayer;
    }
}
