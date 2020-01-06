package dev.cardcast.bullying.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
public abstract class Device implements Serializable {

    @Getter
    @Setter
    private transient Session session;

    @Getter
    @Setter
    private UUID uuid;

}
