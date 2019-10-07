package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameManagerTest {
    private GameManager gameManager;
    private Lobby lobby;
    private Player player;

    public GameManagerTest(){
        gameManager = new GameManager();
        lobby = gameManager.createLobby(true, 2);
        player = new Player();
    }

    @Test
    public void testTryJoinLobby(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        gameManager.tryJoinLobby(player, lobby2.getCode());
        int playerCount = lobby.getQueued().size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount + 1);
    }

    @Test
    public void testAddPlayer(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        int playerCount = lobby2.getQueued().size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount + 1);
    }

    @Test
    public void testRemovePlayer(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        gameManager.addPlayer(lobby2, player);
        int playerCount = lobby2.getQueued().size();
        gameManager.removePlayer(lobby2, player);
        Assertions.assertEquals(lobby2.getQueued().size(), playerCount - 1);
    }

    @Test
    public void testPlayerReadyUp(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        gameManager.addPlayer(lobby2, player);
        boolean isReady = gameManager.playerReadyUp(lobby2, player);
        Assertions.assertTrue(isReady);
    }

    @Test
    public void testStartGame(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        gameManager.addPlayer(lobby2, player);
        gameManager.playerReadyUp(lobby2, player);
        Game game = gameManager.startGame(lobby);
        Assertions.assertNotNull(game);
    }
}
