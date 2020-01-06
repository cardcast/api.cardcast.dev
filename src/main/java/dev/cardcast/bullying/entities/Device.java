package dev.cardcast.bullying.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.util.UUID;

@AllArgsConstructor
public abstract class Device {

    @Getter
    @Setter
    private Session session;

    @Getter
    @Setter
    private UUID uuid;

}
