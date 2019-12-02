package dev.cardcast.bullying.network.events.types;

import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PlayerReadyUpEvent extends Event {

    @Getter
    private final int trackingId;

    @Getter
    private final String name;

    @Getter
    private final String token;

}
