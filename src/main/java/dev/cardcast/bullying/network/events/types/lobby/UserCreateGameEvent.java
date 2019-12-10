package dev.cardcast.bullying.network.events.types.lobby;

import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.common.value.qual.ArrayLen;

public class UserCreateGameEvent extends Event {

    public UserCreateGameEvent(int trackingId, boolean isPublic) {
        this.trackingId = trackingId;
        this.isPublic = isPublic;
    }

    @Getter
    private final int trackingId;

    @Getter
    private final boolean isPublic;

}
