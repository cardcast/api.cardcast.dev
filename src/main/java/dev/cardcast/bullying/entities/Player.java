package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class Player extends Device {

    @Setter
    private String name;

    private Hand hand;

    @Setter
    private boolean doneDrawing;

    public Player(UUID uuid, Session session) {
        super(session, uuid);
        this.hand = new Hand();
        this.doneDrawing = false;
    }

    public Player(UUID uuid) {
        this(uuid, null);
    }
}
