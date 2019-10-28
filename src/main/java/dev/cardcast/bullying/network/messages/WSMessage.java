package dev.cardcast.bullying.network.messages;

import lombok.Getter;

public abstract class WSMessage {

    @Getter
    private String type;

    @Getter
    private final int trackingId;

    public WSMessage() {
        this(-1);
    }

    public WSMessage(int trackingId) {
        String type = this.getClass().getSimpleName();
        if (type.endsWith("Message")) {
            type = type.substring(0, type.length() - 7); // Remove "Message" from the end of the clazz name.
        }

        this.type = type;
        this.trackingId = trackingId;
    }
}
