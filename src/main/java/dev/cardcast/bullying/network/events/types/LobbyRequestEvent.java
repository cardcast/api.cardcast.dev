package dev.cardcast.bullying.network.events.types;

import dev.cardcast.bullying.network.events.Event;

public class LobbyRequestEvent extends Event {

    private final String code;

    public LobbyRequestEvent(String code) {
        this.code = code;
    }
}
