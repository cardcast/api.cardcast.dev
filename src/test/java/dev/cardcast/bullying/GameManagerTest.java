package dev.cardcast.bullying;

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
    public void testFindLobbyByCode(){
        Lobby lobby2 = gameManager.findLobbyByCode(lobby.getCode());
        Assertions.assertEquals(lobby, lobby2);
    }

    @Test
    public void testAddPlayer(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        int playerCount = lobby2.queued.size();
        gameManager.addPlayer(lobby2, player);
        Assertions.assertEquals(lobby2.queued.size(), playerCount + 1);
    }

    @Test
    public void testRemovePlayer(){
        Lobby lobby2 = gameManager.createLobby(true, 2);
        gameManager.addPlayer(lobby2, player);
        int playerCount = lobby2.queued.size();
        gameManager.removePlayer(lobby2, player);
        Assertions.assertEquals(lobby2.queued.size(), playerCount - 1);
    }

    @Test
    public void testStartGame(){
//        Lobby lobby2 = gameManager.createLobby(true, 2);
//        gameManager.addPlayer(lobby2, player);
//        gameManager.startGame(lobby);
//        Assertions.assertEquals(lobby2.queued.size(), playerCount - 1);
    }
}
