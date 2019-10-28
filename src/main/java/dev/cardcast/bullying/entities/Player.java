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

    @Getter @Setter
    private String name;
    @Getter
    private Hand hand;
    @Getter @Setter
    private boolean doneDrawing;

    public Player(Session session) {
        uuid = UUID.randomUUID();
        this.session = session;
        hand = new Hand();
        doneDrawing = false;
    }
}
