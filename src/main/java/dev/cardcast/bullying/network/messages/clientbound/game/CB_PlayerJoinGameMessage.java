package dev.cardcast.bullying.network.messages.clientbound.game;

import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;
import lombok.Getter;

public class CB_PlayerJoinGameMessage extends ClientBoundWSMessage {

    @Getter
    private Player player;

    public CB_PlayerJoinGameMessage(Player player) {
        this.player = player;
    }
}
