package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.UUID;

public class Player {

    @Getter
    private final UUID uuid;

    @Getter
    private Session session;

    @Getter
    private Hand hand;

    @Getter @Setter
    private boolean hasDrawn;

    @Getter @Setter
    private String name;

    public Player(Session session, String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.session = session;
    }
}
