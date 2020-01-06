package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GameManagerTest {
    private GameManager gameManager;
    private Lobby lobby;
    private Player player;

    @BeforeEach
    public void beforeEach() {
        gameManager = new GameManager();
        lobby = gameManager.createLobby(true, 2, new Host(null, UUID.randomUUID()));
        player = new Player(UUID.randomUUID(), null);
        player.setName("Mark");
    }

    @Test
    public void testCreateLobby() {
        int maxPlayers = 2;
        Lobby lobby2 = gameManager.createLobby(true, maxPlayers, new Host(null, UUID.randomUUID()));
        int playerCount = lobby.getMaxPlayers();
        Assertions.assertNotNull(lobby2);
        Assertions.assertEquals(maxPlayers, playerCount);
    }

    @Test
    public void testTryJoinLobby() {
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host(null, UUID.randomUUID()));
        gameManager.tryJoinLobby(player, lobby2.getCode());
        int playerCount = lobby.getPlayers().size();
        Assertions.assertEquals(lobby2.getPlayers().size(), playerCount + 1);
    }

    @Test
    public void testTryJoinNonExistingLobby() {
        Lobby lobby2 = gameManager.tryJoinLobby(player, "test");
        Assertions.assertNull(lobby2);
    }

    @Test
    public void testAddPlayer() {
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host(null, UUID.randomUUID()));
        int playerCount = lobby2.getPlayers().size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getPlayers().size(), playerCount + 1);
    }

    @Test
    public void testAddPlayerToFullLobby() {
        Lobby lobby2 = gameManager.createLobby(true, 0, new Host(null, UUID.randomUUID()));
        int playerCount = lobby2.getPlayers().size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getPlayers().size(), playerCount);
    }

    @Test
    public void testRemovePlayer() {
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host(null, UUID.randomUUID()));
        gameManager.addPlayer(lobby2, player);
        int playerCount = lobby2.getPlayers().size();
        gameManager.removePlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getPlayers().size(), playerCount - 1);
    }


    @Test
    public void testStartGame() {
        Lobby lobby2 = gameManager.createLobby(true, 2, new Host(null, UUID.randomUUID()));
        gameManager.addPlayer(lobby2, player);
        Game hasGameStarted = gameManager.startGame(lobby2);
        Assertions.assertNotNull(hasGameStarted);
    }
}
