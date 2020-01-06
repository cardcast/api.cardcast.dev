package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Host;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
    void beforeEach() {
        GameManager gameManager = new GameManager();
        Lobby lobby = gameManager.createLobby(true, 3, new Host(null, UUID.randomUUID()));

        this.playerOne = new Player(UUID.randomUUID(), null);
        this.playerTwo = new Player(UUID.randomUUID(), null);
        this.playerThree = new Player(UUID.randomUUID(), null);

        gameManager.addPlayer(lobby, playerOne);
        gameManager.addPlayer(lobby, playerTwo);
        gameManager.addPlayer(lobby, playerThree);

        gameManager.startGame(lobby);
        this.game = gameManager.getGames().stream().findFirst().get();
        this.gameLogic = BullyingGameLogic.getInstance();

        pOne = game.getPlayers().get(0);
        pTwo = game.getPlayers().get(1);
        pThree = game.getPlayers().get(2);
    }

    void playCardBasicSetup(Card cardOnStack, Card cardToPlay) {
        gameLogic.startGame(game);
        game.getStack().add(cardOnStack);
        guaranteeSingleCard(pOne, cardToPlay);
    }

    boolean checkBasicTurnPassed() {
        boolean correct;
        correct = game.isTheirTurn(pTwo);
        if (correct) correct = !game.isTheirTurn(pOne);
        if (correct) correct = !game.isTheirTurn(pThree);
        return correct;
    }

    void guaranteeSingleCard(Player player, Card card) {
        List<Card> pCards = player.getHand().getCards();
        if (pCards.contains(card)) {
            pCards.removeIf(card::equals);
        }
        pCards.add(card);
    }

    @Nested
    class StartGameTests {
        @Test
        void testStartGamePlayerHandSize() {
            gameLogic.startGame(game);

            int expected = 7;
            int playerOneHandSize = playerOne.getHand().getCards().size();
            int playerTwoHandSize = playerTwo.getHand().getCards().size();
            int playerThreeHandSize = playerThree.getHand().getCards().size();

            assertEquals(expected, playerOneHandSize);
            assertEquals(expected, playerTwoHandSize);
            assertEquals(expected, playerThreeHandSize);
        }

        @Test
        void testStartGameDeckSize() {
            gameLogic.startGame(game);

            // 54 cards in total on deck minus the cards taken by the players (three times 7) minus the first card put on the stack
            int expectedAmount = 54 - (3 * 7) - 1;

            assertEquals(expectedAmount, game.getDeck().size());
        }

        @Test
        void testStartGameVariableSetup() {
            gameLogic.startGame(game);

            assertTrue(game.isTheirTurn(pOne));
            assertTrue(game.isClockwise());
            assertEquals(0, game.getNumberToDraw());
        }
    }

    @Nested
    class PlayCardTests {

        @Nested
        class ValidPlays {
            @Test
            void testPlayCardBySuit() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardByRank() {
                Card newStack = new Card(Suit.SPADES, Rank.NINE);
                Card playedCard = new Card(Suit.HEARTS, Rank.NINE);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardTwo() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.TWO);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));
                assertEquals(2, game.getNumberToDraw());

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardJoker() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));
                assertEquals(5, game.getNumberToDraw());

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardSeven() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SEVEN);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));
                assertFalse(pOne.isDoneDrawing());

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed()); // no turn passed!
            }

            @Test
            void testPlayCardEight() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.EIGHT);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertFalse(game.isTheirTurn(pOne)); // two turns passed!
                assertFalse(game.isTheirTurn(pTwo));
                assertTrue(game.isTheirTurn(pThree));
            }

            @Test
            void testPlayCardAce() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.ACE);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertFalse(game.isClockwise());
                assertFalse(game.isTheirTurn(pOne)); // direction changed!
                assertFalse(game.isTheirTurn(pTwo));
                assertTrue(game.isTheirTurn(pThree));
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
            void testPlayCardOnOldJoker() {
                Card newStack = new Card(Suit.JOKER, Rank.JOKER);
                Card playedCard = new Card(Suit.CLUBS, Rank.THREE);
                playCardBasicSetup(newStack, playedCard);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedEqualPlayTwo() {
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.HEARTS, Rank.TWO);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(4);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));
                assertEquals(6, game.getNumberToDraw());

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedEqualPlayJoker() {
                Card newStack = new Card(Suit.JOKER, Rank.JOKER);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(7);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));
                assertEquals(12, game.getNumberToDraw());

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedStrongerPlay() {
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(4);

                assertTrue(gameLogic.playCard(game, pOne, playedCard));
                assertEquals(9, game.getNumberToDraw());

                // basic assertions for valid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard));
                assertEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed());
            }
        }

        @Nested
        class InvalidPlays {
            @Test
            void testPlayCardDoNotHaveCard() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().remove(playedCard);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertFalse(pOne.getHand().getCards().contains(playedCard)); // he did not have it!
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardNotYourTurn() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);
                game.setTurnIndex(1);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertTrue(checkBasicTurnPassed()); // it was not their turn!
            }

            @Test
            void testPlayCardNotSameSuitOrColor() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.HEARTS, Rank.NINE);
                playCardBasicSetup(newStack, playedCard);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedNormalCard() {
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.CLUBS, Rank.SIX);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(4);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardBulliedWeakerPlay() {
                Card newStack = new Card(Suit.JOKER, Rank.JOKER);
                Card playedCard = new Card(Suit.CLUBS, Rank.TWO);
                playCardBasicSetup(newStack, playedCard);
                game.setNumberToDraw(7);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardFinishFreebieJack() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.HEARTS, Rank.JACK);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().clear();
                pOne.getHand().getCards().add(playedCard);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardFinishFreebieJoker() {
                Card newStack = new Card(Suit.CLUBS, Rank.THREE);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().clear();
                pOne.getHand().getCards().add(playedCard);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }

            @Test
            void testPlayCardFinishFreebieBullied() {
                Card newStack = new Card(Suit.CLUBS, Rank.TWO);
                Card playedCard = new Card(Suit.JOKER, Rank.JOKER);
                playCardBasicSetup(newStack, playedCard);
                pOne.getHand().getCards().clear();
                pOne.getHand().getCards().add(playedCard);
                game.setNumberToDraw(2);

                assertFalse(gameLogic.playCard(game, pOne, playedCard));

                // basic assertions for invalid playCard
                assertTrue(pOne.getHand().getCards().contains(playedCard));
                assertNotEquals(playedCard, game.getTopCardFromStack());
                assertFalse(checkBasicTurnPassed());
            }
        }
    }

    @Nested
    class DrawCardTests {
        @Test
        void testDrawCard() {
            gameLogic.startGame(game);
            int oldHandSize = pOne.getHand().getCards().size();
            int oldDeckSize = game.getDeck().size();

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(pOne.isDoneDrawing());

            // the actual tests
            assertNotNull(gameLogic.drawCard(game, pOne));
            assertTrue(pOne.isDoneDrawing());
            assertEquals(oldHandSize + 1, pOne.getHand().getCards().size());
            assertEquals(oldDeckSize - 1, game.getDeck().size());
        }

        @Test
        void testDrawCardBullied() {
            gameLogic.startGame(game);
            int oldHandSize = pOne.getHand().getCards().size();
            int oldDeckSize = game.getDeck().size();
            game.setNumberToDraw(4);

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(pOne.isDoneDrawing());

            // the actual tests
            assertNotNull(gameLogic.drawCard(game, pOne));
            assertFalse(pOne.isDoneDrawing());
            assertEquals(oldHandSize + 4, pOne.getHand().getCards().size());
            assertEquals(oldDeckSize - 4, game.getDeck().size());
        }

        @Test
        void testDrawCardNotTheirTurn() {
            gameLogic.startGame(game);
            int oldHandSize = pTwo.getHand().getCards().size();
            int oldDeckSize = game.getDeck().size();

            // make sure the situation is prepared correctly
            assertFalse(game.isTheirTurn(pTwo));

            // the actual tests
            assertNull(gameLogic.drawCard(game, pTwo));
            assertEquals(oldHandSize, pTwo.getHand().getCards().size());
            assertEquals(oldDeckSize, game.getDeck().size());
        }

        @Test
        void testDrawCardDrawnAlready() {
            gameLogic.startGame(game);

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(pOne.isDoneDrawing());
            assertNotNull(gameLogic.drawCard(game, pOne));
            assertTrue(pOne.isDoneDrawing());

            int oldHandSize = pOne.getHand().getCards().size();
            int oldDeckSize = game.getDeck().size();

            // the actual tests
            assertNull(gameLogic.drawCard(game, pOne));
            assertEquals(oldHandSize, pOne.getHand().getCards().size());
            assertEquals(oldDeckSize, game.getDeck().size());
        }
    }

    @Nested
    class EndTurnTests {
        @Test
        void testEndTurn() {
            gameLogic.startGame(game);

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(game.isTheirTurn(pTwo));
            assertFalse(game.isTheirTurn(pThree));
            pOne.setDoneDrawing(true);

            // the actual tests
            assertTrue(gameLogic.endTurn(game, pOne));
            assertTrue(checkBasicTurnPassed());
        }

        @Test
        void testEndTurnCounterClockwise() {
            gameLogic.startGame(game);

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(game.isTheirTurn(pTwo));
            assertFalse(game.isTheirTurn(pThree));
            pOne.setDoneDrawing(true);
            game.setClockwise(false);

            // the actual tests
            assertTrue(gameLogic.endTurn(game, pOne));
            assertFalse(game.isTheirTurn(pOne));
            assertFalse(game.isTheirTurn(pTwo));
            assertTrue(game.isTheirTurn(pThree));
        }

        @Test
        void testEndTurnNotTheirTurn() {
            gameLogic.startGame(game);

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(game.isTheirTurn(pTwo));

            // the actual tests
            assertFalse(gameLogic.endTurn(game, pTwo));
            assertFalse(checkBasicTurnPassed());
        }

        @Test
        void testEndTurnNotDoneDrawing() {
            gameLogic.startGame(game);

            // make sure the situation is prepared correctly
            assertTrue(game.isTheirTurn(pOne));
            assertFalse(pOne.isDoneDrawing());

            // the actual tests
            assertFalse(gameLogic.endTurn(game, pOne));
            assertFalse(checkBasicTurnPassed());
        }
    }
}
