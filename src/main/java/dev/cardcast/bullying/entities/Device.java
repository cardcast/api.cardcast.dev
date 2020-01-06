package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.util.zip.DeflaterInputStream;

public abstract class Device {

    @Getter
    @Setter
    private Session session;

    public Device(Session session) {
        this.session = session;
    }

}
