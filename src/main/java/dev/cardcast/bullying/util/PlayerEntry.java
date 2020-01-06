package dev.cardcast.bullying.util;

import dev.cardcast.bullying.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;

@AllArgsConstructor
public class PlayerEntry {

    @Getter
    @Setter
    private Session session;


    @Getter
    private final Player player;

}
