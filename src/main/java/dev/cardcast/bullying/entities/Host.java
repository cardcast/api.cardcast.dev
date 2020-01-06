package dev.cardcast.bullying.entities;

import javax.websocket.Session;
import java.util.UUID;

public class Host extends Device {

    public Host(Session session, UUID uuid) {
        super(session, uuid);
    }
}
