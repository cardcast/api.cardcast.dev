package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Lobby;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;

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

        this.playerOne = new Player(null, "Alice");
        this.playerTwo = new Player(null, "Bob");
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

    void playCardBasicSetup(Card cardOnStack, Card cardToPlay){
        gameLogic.startGame(game);
        game.getStack().add(cardOnStack);
        guaranteeSingleCard(pOne, cardToPlay);
    }

    boolean checkBasicTurnPassed(){
        boolean correct;
        correct = game.isTheirTurn(pTwo);
        if (correct) correct = !game.isTheirTurn(pOne);
        if (correct) correct = !game.isTheirTurn(pThree);
        return correct;
    }

    void guaranteeSingleCard(Player player, Card card){
        List<Card> pCards = player.getHand().getCards();
        if (pCards.contains(card)){
            pCards.removeIf(card::equals);
        }
        pCards.add(card);
    }

    @Nested
    class StartGameTests{
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
    }

    @Nested
    class PlayCardTests{

        @Nested
        class ValidPlays{
            @Test
            void testPlayCardBySuit(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardByRank(){
                Card newStack = new Card(Suit.SPADES, Rank.NINE);
                Card playedCard = new Card(Suit.HEARTS, Rank.NINE);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardTwo(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.TWO);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));
                Assertions.assertEquals(2, game.getNumberToDraw());

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardJoker(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));
                Assertions.assertEquals(5, game.getNumberToDraw());

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardSeven(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SEVEN);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));
                Assertions.assertFalse(pOne.isDoneDrawing());

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed()); // no turn passed!
            }

            @Test
            void testPlayCardEight(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.EIGHT);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(game.isTheirTurn(pOne)); // two turns passed!
                Assertions.assertFalse(game.isTheirTurn(pTwo));
                Assertions.assertTrue(game.isTheirTurn(pThree));
            }

            @Test
            void testPlayCardAce(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.ACE);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(game.isClockwise());
                Assertions.assertFalse(game.isTheirTurn(pOne)); // direction changed!
                Assertions.assertFalse(game.isTheirTurn(pTwo));
                Assertions.assertTrue(game.isTheirTurn(pThree));
            }

            /*
                // TODO: THE EFFECTS OF A 10 AND A JACK

                @Test
                void testPlayCardTen(){}

                @Test
                void testPlayCardJack(){}

                @Test
                void testPlayCardJackOnOtherSuit(){}
            */

            @Test
            void testPlayCardOnOldJoker(){
                Card newStack = new Card(Suit.JOKER, Rank.JOKER);
                Card playedCard = new Card(Suit.CLUBS, Rank.THREE);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedEqualPlayTwo(){
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.HEARTS, Rank.TWO);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(4);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));
                Assertions.assertEquals(6, game.getNumberToDraw());

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedEqualPlayJoker(){
                Card newStack = new Card(Suit.JOKER, Rank.JOKER);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(7);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));
                Assertions.assertEquals(12, game.getNumberToDraw());

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedStrongerPlay(){
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(4);

                Assertions.assertTrue(gameLogic.playCard(game, pOne, playedCard));
                Assertions.assertEquals(9, game.getNumberToDraw());

                // basic assertions for valid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed());
            }
        }

        @Nested
        class InvalidPlays{
            @Test
            void testPlayCardDoNotHaveCard(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().remove(playedCard);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertFalse(pOne.getHand().getCards().contains(playedCard)); // he did not have it!
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardNotYourTurn(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);
                game.setTurnIndex(1);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertTrue(checkBasicTurnPassed()); // it was not their turn!
            }

            @Test
            void testPlayCardNotSameSuitOrColor(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.HEARTS, Rank.NINE);
                playCardBasicSetup(newStack, playedCard);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedNormalCard(){
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(4);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedWeakerPlay(){
                Card newStack = new Card(Suit.JOKER, Rank.JOKER);
                Card playedCard = new Card(Suit.CLUBS, Rank.TWO);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(7);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardFinishFreebieJack(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.HEARTS, Rank.JACK);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().clear();
                pOne.getHand().getCards().add(playedCard);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardFinishFreebieJoker(){
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().clear();
                pOne.getHand().getCards().add(playedCard);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardFinishFreebieBullied(){
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().clear();
                pOne.getHand().getCards().add(playedCard);
                game.setNumberToDraw(2);

                Assertions.assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                Assertions.assertTrue(pOne.getHand().getCards().contains(playedCard));
                Assertions.assertNotEquals(playedCard, game.getTopCardFromStack());
                Assertions.assertFalse(checkBasicTurnPassed());
            }
        }
    }

    @Nested
    class DrawCardTests{
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
    }

    @Nested
    class EndTurnTests{
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
            Assertions.assertTrue(checkBasicTurnPassed());
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
            Assertions.assertFalse(checkBasicTurnPassed());
        }

        @Test
        void testEndTurnNotDoneDrawing(){
            gameLogic.startGame(game);

            // make sure the situation is prepared correctly
            Assertions.assertTrue(game.isTheirTurn(pOne));
            Assertions.assertFalse(pOne.isDoneDrawing());

            // the actual tests
            Assertions.assertFalse(gameLogic.endTurn(game, pOne));
            Assertions.assertFalse(checkBasicTurnPassed());
        }
    }
}
