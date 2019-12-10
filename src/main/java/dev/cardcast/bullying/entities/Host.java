package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;

public class Host {
    @Getter @Setter
    private Session session;
}
