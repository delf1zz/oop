package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {

    private static class TestDeck extends Deck {
        private final List<Card> presetCards;

        public TestDeck(List<Card> presetCards) {
            this.presetCards = new ArrayList<>(presetCards);
        }

        @Override
        public Card drawCard() {
            if (!presetCards.isEmpty()) {
                return presetCards.remove(0);
            }
            return super.drawCard();
        }
    }

    private Scanner createScannerWithInput(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return new Scanner(new ByteArrayInputStream(bytes));
    }

    @Test
    void testImmediatePlayerBlackjack() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.ACE),
                new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
                new Card(Card.Suit.CLUBS, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN)
        );
        String input = "0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testImmediateDealerBlackjack() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.SEVEN),
                new Card(Card.Suit.SPADES, Card.Rank.ACE),
                new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN)
        );
        String input = "0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testBothImmediateBlackjack() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.ACE),
                new Card(Card.Suit.SPADES, Card.Rank.ACE),
                new Card(Card.Suit.CLUBS, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN)
        );
        String input = "0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testPlayerStopsImmediately() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN)
        );
        String input = "0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testPlayerBustsInTurn() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN),
                new Card(Card.Suit.HEARTS, Card.Rank.TEN)
        );
        String input = "1\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testPlayerHitsWithoutBustThenStops() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.TWO),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.THREE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN),
                new Card(Card.Suit.HEARTS, Card.Rank.FOUR)
        );
        String input = "1\n0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testDealerBust() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.EIGHT),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
                new Card(Card.Suit.HEARTS, Card.Rank.TEN)
        );
        String input = "0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testPlayerWinsAndTieAndDealerWins() {
        List<Card> cards = List.of(
                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN),

                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.EIGHT),
                new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT),

                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN)
        );
        String input = "0\n1\n0\n1\n0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testInvalidInputInPlayerTurn() {
        String input = "abc\n0\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testInvalidChoiceInPlayerTurn() {
        String input = "invalid\n0\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testQuitWithZero() {
        String input = "0\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));
        assertDoesNotThrow(game::start);
    }

    @Test
    void testPlayerBustImmediateBreak() {
        String input = "1\n1\n1\n1\n1\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));
        assertDoesNotThrow(game::start);
    }
}