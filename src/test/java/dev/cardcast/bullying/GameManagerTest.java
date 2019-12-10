package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameManagerTest {
    private GameManager gameManager;
    private Lobby lobby;
    private Player player;

    @BeforeEach
    public void beforeEach(){
        gameManager = new GameManager();
        lobby = gameManager.createLobby(true, 2, new Host());
        player = new Player(null, "Mark");
    }

    @Test
    public void testCreateLobby(){
        int maxPlayers = 2;
        Lobby lobby2 = gameManager.createLobby(true, maxPlayers, new Host());
        int playerCount = lobby.getMaxPlayers();
        Assertions.assertNotNull(lobby2);
        Assertions.assertEquals(maxPlayers, playerCount);
    }

    @Test
    public void testTryJoinLobby(){
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host());
        gameManager.tryJoinLobby(player, lobby2.getCode());
        int playerCount = lobby.getQueued().size();
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount + 1);
    }

    @Test
    public void testTryJoinNonExistingLobby(){
        Lobby lobby2 = gameManager.tryJoinLobby(player, "test");
        Assertions.assertNull(lobby2);
    }

    @Test
    public void testAddPlayer(){
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host());
        int playerCount = lobby2.getQueued().size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount + 1);
    }

    @Test
    public void testAddPlayerToFullLobby(){
        Lobby lobby2 = gameManager.createLobby(true, 0, new Host());
        int playerCount = lobby2.getQueued().size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount);
    }

    @Test
    public void testRemovePlayer(){
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host());
        gameManager.addPlayer(lobby2, player);
        int playerCount = lobby2.getQueued().size();
        gameManager.removePlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount - 1);
    }



    @Test
    public void testStartGame(){
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host());
        gameManager.addPlayer(lobby2, player);
        boolean hasGameStarted = gameManager.startGame(lobby2);
        Assertions.assertTrue(hasGameStarted);
    }
}
