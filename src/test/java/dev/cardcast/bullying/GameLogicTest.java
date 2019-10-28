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

        this.player = new Player(null);
        gameManager.addPlayer(lobby, player);

        this.playerTwo = new Player(null);
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
        gameLogic.startGame(game);

        Assertions.assertEquals(player.getHand().getCards().size() + playerTwo.getHand().getCards().size(), 7 + 7);
    }

    @Test
    public void testDrawCard(){
        gameLogic.startGame(game);
        int playerCardAmount = player.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();

        gameLogic.drawCard(game, player);

        Assertions.assertEquals(player.getHand().getCards().size(), playerCardAmount + 1);
        Assertions.assertEquals(game.getDeck().size(), deckCardAmount - 1);
    }


    // @Test
    void testPlayCardCannotPlayNonBullyingCardOnBullyingCard(){
        gameLogic.startGame(game);

        gameLogic.playCard(game, player, new Card(Suit.CLUBS, Rank.TWO));
        boolean doesAllowCardPlay = CardRules.getInstance().validPlay(game, playerTwo, new Card(Suit.SPADES, Rank.THREE));

        Assertions.assertFalse(doesAllowCardPlay);
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

        int playerCardAmount = player.getHand().getCards().size();

        Assertions.assertEquals(7, playerCardAmount);
    }

    @Test
    void testStartGameDeckRightCardAmount(){
        gameLogic.startGame(game);
        int playerCardAmount = player.getHand().getCards().size();
        int playerTwoCardAmount = playerTwo.getHand().getCards().size();
        int deckCardAmount = game.getDeck().size();

        // 54 cards in total on deck minus the cards taken by the players minus the first card drawn to the middle.
        int expectedAmount = 54 - playerCardAmount - playerTwoCardAmount - 1;

        Assertions.assertEquals(expectedAmount, deckCardAmount);
    }
}
