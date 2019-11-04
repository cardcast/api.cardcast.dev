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
    private Player playerThree;
    private Player pOne; // these ones are based on index
    private Player pTwo;
    private Player pThree;
    private BullyingGameLogic gameLogic;
    private Game game;

    @BeforeEach
    void beforeEach(){
        GameManager gameManager = new GameManager();
        Lobby lobby = gameManager.createLobby(true, 3);

        this.playerOne = new Player(null, "Alpha");
        this.playerTwo = new Player(null, "Beta");
        this.playerThree = new Player(null, "Charlie");

        gameManager.addPlayer(lobby, playerOne);
        gameManager.addPlayer(lobby, playerTwo);
        gameManager.addPlayer(lobby, playerThree);

        gameManager.playerReadyUp(lobby, playerOne);
        gameManager.playerReadyUp(lobby, playerTwo);
        gameManager.playerReadyUp(lobby, playerThree);

        this.game = gameManager.startGame(lobby);
        this.gameLogic = BullyingGameLogic.getInstance();

        pOne = game.getPlayers().get(0);
        pTwo = game.getPlayers().get(1);
        pThree = game.getPlayers().get(2);
    }

    // region testStartGame()

    @Test
    void testStartGamePlayerHandSize(){
        gameLogic.startGame(game);

        int expected = 7;
        int playerOneHandSize = playerOne.getHand().getCards().size();
        int playerTwoHandSize = playerTwo.getHand().getCards().size();
        int playerThreeHandSize = playerThree.getHand().getCards().size();

        Assertions.assertEquals(expected, playerOneHandSize);
        Assertions.assertEquals(expected, playerTwoHandSize);
        Assertions.assertEquals(expected, playerThreeHandSize);
    }

    @Test
    void testStartGameDeckSize(){
        gameLogic.startGame(game);

        // 54 cards in total on deck minus the cards taken by the players (three times 7) minus the first card put on the stack
        int expectedAmount = 54 - (3 * 7) - 1;

        Assertions.assertEquals(expectedAmount, game.getDeck().size());
    }

    @Test
    void testStartGameVariableSetup(){
        gameLogic.startGame(game);

        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertTrue(game.isClockwise());
        Assertions.assertEquals(0, game.getNumberToDraw());
    }

//    @Test
//    public void testShuffleCards(){
//        List<Card> currentDeck = game.getDeck();
//        gameLogic
//        List<Card> newDeck = game.getDeck();
//        Assertions.assertNotEquals(currentDeck, newDeck);
//    }

    // endregion

    // region testPlayCard()

    // TODO: WRITE TESTS FOR BullyingGameLogic.playCard()
    /*
        play a card that you don't have
        play a card while it is not your turn
        - it is not a valid play
            - you are being bullied...
                but try to play a normal card
                but try to play a 2 on a joker
                but try to finish with a joker
            you try to finish with a freebie (Jack or Joker)
            the card does not have the same suit or color
        - it is a valid play
            BECAUSE you play a Jack
            BECAUSE you play a Joker
            it is a two
            it is a joker
            it is a seven
            it is an eight
            it is an ace
            it is a ten
            it is a jack?
            it is nothing special
    */


    // endregion

    // region testDrawCard()

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

    // endregion

    // region testEndTurn()

    @Test
    void testEndTurn(){
        gameLogic.startGame(game);

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(game.isTheirTurn(pTwo));
        Assertions.assertFalse(game.isTheirTurn(pThree));
        pOne.setDoneDrawing(true);

        // the actual tests
        Assertions.assertTrue(gameLogic.endTurn(game, pOne));
        Assertions.assertFalse(game.isTheirTurn(pOne));
        Assertions.assertTrue(game.isTheirTurn(pTwo));
        Assertions.assertFalse(game.isTheirTurn(pThree));
    }

    @Test
    void testEndTurnCounterClockwise(){
        gameLogic.startGame(game);

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(game.isTheirTurn(pTwo));
        Assertions.assertFalse(game.isTheirTurn(pThree));
        pOne.setDoneDrawing(true);
        game.setClockwise(false);

        // the actual tests
        Assertions.assertTrue(gameLogic.endTurn(game, pOne));
        Assertions.assertFalse(game.isTheirTurn(pOne));
        Assertions.assertFalse(game.isTheirTurn(pTwo));
        Assertions.assertTrue(game.isTheirTurn(pThree));
    }

    @Test
    void testEndTurnNotTheirTurn(){
        gameLogic.startGame(game);

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(game.isTheirTurn(pTwo));

        // the actual tests
        Assertions.assertFalse(gameLogic.endTurn(game, pTwo));
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(game.isTheirTurn(pTwo));
        Assertions.assertFalse(game.isTheirTurn(pThree));
    }

    @Test
    void testEndTurnNotDoneDrawing(){
        gameLogic.startGame(game);

        // make sure the situation is prepared correctly
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(pOne.isDoneDrawing());

        // the actual tests
        Assertions.assertFalse(gameLogic.endTurn(game, pOne));
        Assertions.assertTrue(game.isTheirTurn(pOne));
        Assertions.assertFalse(game.isTheirTurn(pTwo));
        Assertions.assertFalse(game.isTheirTurn(pThree));
    }

    // endregion
}
