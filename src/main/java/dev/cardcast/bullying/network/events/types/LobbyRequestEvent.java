package dev.cardcast.bullying.network.events.types;

import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LobbyRequestEvent extends Event {

    @Getter
    private final String code;

}
