package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameLogicTest {
    private Player player;
    private BullyingGameLogic gameLogic;
    private Game game;

    public GameLogicTest(){
        GameManager gameManager = new GameManager();
        Lobby lobby = gameManager.createLobby(true, 2);
        player = new Player();
        gameManager.addPlayer(lobby, player);
        gameManager.playerReadyUp(lobby, player);
        game = gameManager.startGame(lobby);
        gameLogic = BullyingGameLogic.getInstance();
    }

//    @Test
//    public void testShuffleCards(){
//        List<Card> currentDeck = game.getDeck();
//        gameLogic.(game);
//        List<Card> newDeck = game.getDeck();
//        Assertions.assertNotEquals(currentDeck, newDeck);
//    }

//    @Test
//    public void testDistributeCards(){
//        Player player1 = game.getPlayers().get(0);
//        gameLogic.distributeCards(game);
//        Assertions.assertEquals(player1.getHand().getCards().size(), 7);
//    }

    @Test
    public void testDrawCard(){
        Player player1 = game.getPlayers().get(0);
        int playerCardAmount = player.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();
        gameLogic.drawCard(game, player1);
        Assertions.assertEquals(player1.getHand().getCards().size(), playerCardAmount + 1);
        Assertions.assertEquals(game.getDeck().size(), deckCardAmount - 1);
    }

    @Test
    public void testPlayCard(){
        Player player1 = game.getPlayers().get(0);
        int playerCardAmount = player.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();
        gameLogic.playCard(game, player1, new Card(Suit.CLUBS, Rank.ACE));
        Assertions.assertEquals(player1.getHand().getCards().size(), playerCardAmount - 1);
        Assertions.assertEquals(game.getDeck().size(), deckCardAmount + 1);
    }

    @Test
    public void testStartGame(){
        gameLogic.startGame(game);
        Player player1 = game.getPlayers().get(0);
        int playerCardAmount = player1.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();
        int stackCardAmount = game.getStack().size();
        Assertions.assertEquals(playerCardAmount, 7);
        Assertions.assertEquals(deckCardAmount, 51);
        Assertions.assertEquals(stackCardAmount, 1);
    }
}
