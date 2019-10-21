package dev.cardcast.bullying.network.messages.serverbound.game;

import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.types.PlayerDrawCardEvent;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.network.messages.serverbound.lobby.SB_RequestLobbyMessage;
import lombok.AllArgsConstructor;

public class SB_PlayerDrawCardMessage extends ServerBoundWSMessage {

    public SB_PlayerDrawCardMessage() {

    }

    @Override
    public Event getEvent() {
        return new PlayerDrawCardEvent();
    }
}
