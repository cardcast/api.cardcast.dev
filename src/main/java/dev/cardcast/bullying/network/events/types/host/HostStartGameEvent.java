package dev.cardcast.bullying.network.events.types.host;

import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HostStartGameEvent extends Event {

    @Getter
    private final int trackingId;
}
