package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameLogicTest {
    private Player player;
    private Player playerTwo;
    private BullyingGameLogic gameLogic;
    private Game game;

    @BeforeEach
    public void beforeEach(){
        GameManager gameManager = new GameManager();
        Lobby lobby = gameManager.createLobby(true, 2);

        this.player = new Player(null, "Mark");
        gameManager.addPlayer(lobby, player);

        this.playerTwo = new Player(null, "Steven");
        gameManager.addPlayer(lobby, playerTwo);

        gameManager.playerReadyUp(lobby, player);
        gameManager.playerReadyUp(lobby, playerTwo);

        this.game = gameManager.startGame(lobby);
        this.gameLogic = BullyingGameLogic.getInstance();
    }

//    @Test
//    public void testShuffleCards(){
//        List<Card> currentDeck = game.getDeck();
//        gameLogic
//        List<Card> newDeck = game.getDeck();
//        Assertions.assertNotEquals(currentDeck, newDeck);
//    }

    @Test
    public void testDistributeCards(){
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        gameLogic.startGame(game);

        Assertions.assertEquals(player1.getHand().getCards().size() + player2.getHand().getCards().size(), 7 + 7);
    }

    @Test
    public void testDrawCard(){
        gameLogic.startGame(game);
        Player player1 = game.getPlayers().get(0);
        int playerCardAmount = player.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();

        gameLogic.drawCard(game, player1);

        Assertions.assertEquals(player1.getHand().getCards().size(), playerCardAmount + 1);
        Assertions.assertEquals(game.getDeck().size(), deckCardAmount - 1);
    }

//    @Test
//    public void testPlayCard(){
//        gameLogic.startGame(game);
//        Player player1 = game.getPlayers().get(0);
//        int playerCardAmount = player.getHand().getCards().size();
//        int deckCardAmount = game.getDeck().size();
//
//        gameLogic.playCard(game, player1, new Card(Suit.CLUBS, Rank.ACE));
//
//        Assertions.assertEquals(player1.getHand().getCards().size(), playerCardAmount - 1);
//        Assertions.assertEquals(game.getDeck().size(), deckCardAmount + 1);
//    }

    @Test
    void testStartGamePlayerRightCardAmount(){
        gameLogic.startGame(game);

        Player player1 = game.getPlayers().get(0);
        int playerCardAmount = player1.getHand().getCards().size();

        Assertions.assertEquals(7, playerCardAmount);
    }

    @Test
    void testStartGameDeckRightCardAmount(){
        gameLogic.startGame(game);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        int playerCardAmount = player1.getHand().getCards().size();
        int playerTwoCardAmount = player2.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();

        Assertions.assertEquals(54 - playerCardAmount - playerTwoCardAmount, deckCardAmount);
    }
}
