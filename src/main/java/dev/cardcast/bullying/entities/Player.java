package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class Player implements Serializable {

    private final UUID uuid;

    @Setter
    private transient Session session;

    @Setter
    private String name;

    private Hand hand;

    @Setter
    private boolean doneDrawing;

    public Player(Session session, String name) {
        this.hand = new Hand();
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.session = session;
        hand = new Hand();
        doneDrawing = false;
    }
}
