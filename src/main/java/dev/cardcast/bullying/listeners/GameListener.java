package dev.cardcast.bullying.listeners;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.network.GameConnector;
import dev.cardcast.bullying.network.NetworkService;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.types.PlayerReadyUpEvent;

import javax.websocket.Session;

public class GameListener implements EventListener {


    @EventHandler
    public void readyUp(Session session, PlayerReadyUpEvent event) {


//        Player sessionPlayer = players.stream().filter(player -> player.getSession().equals(session)).findFirst().orElse(null);
//        if (sessionPlayer != null) {
//            sessionPlayer.setName(event.getName());
//        }
    }

    @EventHandler
    public void playCard(Session session) {

    }
}
