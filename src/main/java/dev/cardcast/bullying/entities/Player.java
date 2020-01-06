package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class Player extends Device implements Serializable {

    private transient final UUID uuid;

    @Setter
    private String name;

    private Hand hand;

    @Setter
    private boolean doneDrawing;

    public Player(Session session, String name) {
        super(session);
        this.hand = new Hand();
        this.uuid = UUID.randomUUID();
        this.name = name;
        hand = new Hand();
        doneDrawing = false;
    }
}
