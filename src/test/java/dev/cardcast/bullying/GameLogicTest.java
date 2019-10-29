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

public class GameLogicTest {
    private Player playerOne; // these ones are based on addition order
    private Player playerTwo;
    private Player pOne; // these ones are based on index
    private Player pTwo;
    private BullyingGameLogic gameLogic;
    private Game game;

    @BeforeEach
    void beforeEach(){
        GameManager gameManager = new GameManager();
        Lobby lobby = gameManager.createLobby(true, 2);

        this.playerOne = new Player(null);
        gameManager.addPlayer(lobby, playerOne);

        this.playerTwo = new Player(null);
        gameManager.addPlayer(lobby, playerTwo);

        gameManager.playerReadyUp(lobby, playerOne);
        gameManager.playerReadyUp(lobby, playerTwo);

        this.game = gameManager.startGame(lobby);
        this.gameLogic = BullyingGameLogic.getInstance();

        pOne = game.getPlayers().get(0);
        pTwo = game.getPlayers().get(1);
    }

    @Test
    void testStartGamePlayerHandSize(){
        gameLogic.startGame(game);

        int expected = 7;
        int playerOneHandSize = playerOne.getHand().getCards().size();
        int playerTwoHandSize = playerTwo.getHand().getCards().size();

        Assertions.assertEquals(expected, playerOneHandSize);
        Assertions.assertEquals(expected, playerTwoHandSize);
    }
    @Test
    void testStartGameDeckSize(){
        gameLogic.startGame(game);

        // 54 cards in total on deck minus the cards taken by the players (two times 7) minus the first card put on the stack
        int expectedAmount = 54 - 7 - 7 - 1;

        Assertions.assertEquals(expectedAmount, game.getDeck().size());
    }
    @Test
    void testStartGameVariableSetup(){
        gameLogic.startGame(game);

        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertTrue(game.isClockwise());
        Assertions.assertEquals(0, game.getNumberToDraw());
    }

    @Test
    void testDrawCard(){
        gameLogic.startGame(game);
        int oldHandSize = pOne.getHand().getCards().size();
        int oldDeckSize = game.getDeck().size();

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(pOne.isDoneDrawing());

        // the actual tests
        Assertions.assertTrue(gameLogic.drawCard(game, pOne));
        Assertions.assertTrue(pOne.isDoneDrawing());
        Assertions.assertEquals(oldHandSize + 1, pOne.getHand().getCards().size());
        Assertions.assertEquals(oldDeckSize - 1, game.getDeck().size());
    }
    @Test
    void testDrawCardBullied(){
        gameLogic.startGame(game);
        int oldHandSize = pOne.getHand().getCards().size();
        int oldDeckSize = game.getDeck().size();
        game.setNumberToDraw(4);

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(pOne.isDoneDrawing());

        // the actual tests
        Assertions.assertTrue(gameLogic.drawCard(game, pOne));
        Assertions.assertFalse(pOne.isDoneDrawing());
        Assertions.assertEquals(oldHandSize + 4, pOne.getHand().getCards().size());
        Assertions.assertEquals(oldDeckSize - 4, game.getDeck().size());
    }
    @Test
    void testDrawCardNotTheirTurn(){
        gameLogic.startGame(game);
        int oldHandSize = pTwo.getHand().getCards().size();
        int oldDeckSize = game.getDeck().size();

        // make sure the situation is prepared correctly
        Assertions.assertFalse(game.isTheirTurn(pTwo));

        // the actual tests
        Assertions.assertFalse(gameLogic.drawCard(game, pTwo));
        Assertions.assertEquals(oldHandSize, pTwo.getHand().getCards().size());
        Assertions.assertEquals(oldDeckSize, game.getDeck().size());
    }
    @Test
    void testDrawCardDrawnAlready(){
        gameLogic.startGame(game);

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(pOne.isDoneDrawing());
        Assertions.assertTrue(gameLogic.drawCard(game, pOne));
        Assertions.assertTrue(pOne.isDoneDrawing());

        int oldHandSize = pOne.getHand().getCards().size();
        int oldDeckSize = game.getDeck().size();

        // the actual tests
        Assertions.assertFalse(gameLogic.drawCard(game, pOne));
        Assertions.assertEquals(oldHandSize, pOne.getHand().getCards().size());
        Assertions.assertEquals(oldDeckSize, game.getDeck().size());
    }


    // TODO: REWORK AND CHECK ALL OLD TESTS WRITTEN BELOW

//    @Test
//    public void testShuffleCards(){
//        List<Card> currentDeck = game.getDeck();
//        gameLogic
//        List<Card> newDeck = game.getDeck();
//        Assertions.assertNotEquals(currentDeck, newDeck);
//    }

    // @Test
    void testPlayCardCannotPlayNonBullyingCardOnBullyingCard(){
        gameLogic.startGame(game);

        gameLogic.playCard(game, playerOne, new Card(Suit.CLUBS, Rank.TWO));
        boolean doesAllowCardPlay = CardRules.getInstance().validPlay(game, playerTwo, new Card(Suit.SPADES, Rank.THREE));

        Assertions.assertFalse(doesAllowCardPlay);
    }

    // @Test
    void testPlayCardCannotEndWithBullyCard(){
        gameLogic.startGame(game);
        
        playerOne.getHand().getCards().clear();
        Card card = new Card(Suit.JOKER, Rank.JOKER);
        playerOne.getHand().getCards().add(card);

        boolean isPlacable = gameLogic.playCard(game, playerOne, card);

        Assertions.assertFalse(isPlacable);
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


}
