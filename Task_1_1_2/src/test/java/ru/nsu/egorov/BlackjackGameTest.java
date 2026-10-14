package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Модульные тесты для проверки игровой логики Блэкджека.
 */
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
    @DisplayName("Проверка Блэкджека у игрока при первом раздаче")
    void testImmediatePlayerBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.SPADES, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.SEVEN)
        );
        String input = "0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertTrue(game.getPlayer().getHand().isBlackjack());
        assertEquals(21, game.getPlayer().getHand().getScore());
    }

    @Test
    @DisplayName("Проверка Блэкджека у дилера при первом раздаче")
    void testImmediateDealerBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.SEVEN),
                new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.TEN)
        );
        String input = "0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertTrue(game.getDealer().getHand().isBlackjack());
        assertEquals(21, game.getDealer().getHand().getScore());
    }

    @Test
    @DisplayName("Проверка одновременно Блэкджека у обоих игроков")
    void testBothImmediateBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.TEN)
        );
        String input = "0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertTrue(game.getPlayer().getHand().isBlackjack());
        assertTrue(game.getDealer().getHand().isBlackjack());
        assertEquals(0, game.getPlayer().getScore());
        assertEquals(0, game.getDealer().getScore()); // Ничья
    }

    @Test
    @DisplayName("Проверка мгновенного паса игрока (остановиться)")
    void testPlayerStopsImmediately() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.SEVEN)
        );
        String input = "0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertEquals(17, game.getPlayer().getHand().getScore());
        assertEquals(17, game.getDealer().getHand().getScore());
    }

    @Test
    @DisplayName("Проверка перебора очков у игрока (Bust)")
    void testPlayerBustsInTurn() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.TEN),
                new Card(Suit.HEARTS, Rank.TEN)
        );
        String input = "1\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertTrue(game.getPlayer().getHand().isBust());
        assertEquals(1, game.getDealer().getScore());
    }

    @Test
    @DisplayName("Проверка добора карты игроком без перебора с последующим пасом")
    void testPlayerHitsWithoutBustThenStops() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.THREE),
                new Card(Suit.DIAMONDS, Rank.TEN),
                new Card(Suit.HEARTS, Rank.FOUR)
        );
        String input = "1\n0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertEquals(9, game.getPlayer().getHand().getScore());
        assertFalse(game.getPlayer().getHand().isBust());
    }

    @Test
    @DisplayName("Проверка перебора у дилера (Dealer Bust)")
    void testDealerBust() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.EIGHT),
                new Card(Suit.DIAMONDS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.TEN)
        );
        String input = "0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertTrue(game.getDealer().getHand().isBust());
        assertEquals(1, game.getPlayer().getScore());
    }

    @Test
    @DisplayName("Проверка нескольких раундов: победа игрока, ничья и победа дилера")
    void testPlayerWinsAndTieAndDealerWins() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.SEVEN),

                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.EIGHT),
                new Card(Suit.DIAMONDS, Rank.EIGHT),

                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.TEN)
        );
        String input = "0\n0\n0\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();
        assertEquals(1, game.getPlayer().getScore());

        game.playRound();
        assertEquals(1, game.getPlayer().getScore());
        assertEquals(0, game.getDealer().getScore());

        game.playRound();
        assertEquals(1, game.getPlayer().getScore());
        assertEquals(1, game.getDealer().getScore());
    }

    @Test
    @DisplayName("Обработка некорректного ввода букв на ходу игрока")
    void testInvalidInputInPlayerTurn() {
        String input = "abc\n0\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));

        game.playRound();

        assertFalse(game.getPlayer().getHand().getCards().isEmpty());
    }

    @Test
    @DisplayName("Обработка некорректной команды на ходу игрока")
    void testInvalidChoiceInPlayerTurn() {
        String input = "invalid\n0\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));

        game.playRound();

        assertFalse(game.getPlayer().getHand().getCards().isEmpty());
    }

    @Test
    @DisplayName("Проверка завершения игры при вводе 0 на предложение следующего раунда")
    void testQuitWithZero() {
        String input = "0\n0\n";
        BlackjackGame game = new BlackjackGame(createScannerWithInput(input));

        game.start();

        assertEquals(1, game.getPlayer().getHand().getCards().size() > 0 ? 1 : 0);
    }

    @Test
    @DisplayName("Автоматическое завершение хода игрока при переборе после серии доборов")
    void testPlayerBustImmediateBreak() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.SEVEN),
                new Card(Suit.HEARTS, Rank.TEN)
        );
        String input = "1\n";
        BlackjackGame game = new BlackjackGame(
                createScannerWithInput(input), new TestDeck(cards));

        game.playRound();

        assertTrue(game.getPlayer().getHand().isBust());
    }

    @Test
    @DisplayName("Проверка успешного ввода валидного количества колод (> 0)")
    void testReadNumberOfDecksValidInput() {
        Scanner scanner = createScannerWithInput("2\n");
        ConsoleWork ui = new ConsoleWork(scanner);

        int result = ui.readNumberOfDecks();

        assertEquals(2, result);
    }

    @Test
    @DisplayName("Проверка конструктора по умолчанию BlackjackGame(scanner)")
    void testDefaultConstructor() {
        Scanner scanner = createScannerWithInput("0\n0\n");
        BlackjackGame game = new BlackjackGame(scanner);
        assertEquals(0, game.getPlayer().getScore());
    }

    @Test
    @DisplayName("Проверка ввода пустого значения при выборе колод")
    void testReadNumberOfDecksEmptyInput() {
        Scanner scanner = createScannerWithInput("\n");
        ConsoleWork ui = new ConsoleWork(scanner);
        assertEquals(1, ui.readNumberOfDecks());
    }

    @Test
    @DisplayName("Проверка ввода отрицательного числа колод (покрытие decks < 1)")
    void testReadNumberOfDecksNegativeInput() {
        Scanner scanner = createScannerWithInput("-5\n");
        ConsoleWork ui = new ConsoleWork(scanner);
        assertEquals(1, ui.readNumberOfDecks());
    }

    @Test
    @DisplayName("Проверка ввода некорректной строки (букв) при выборе колод")
    void testReadNumberOfDecksInvalidFormat() {
        Scanner scanner = createScannerWithInput("abc\n");
        ConsoleWork ui = new ConsoleWork(scanner);
        assertEquals(1, ui.readNumberOfDecks());
    }

    @Test
    @DisplayName("Проверка цикла повторного запроса при ошибочном вводе в askHitOrStand")
    void testAskHitOrStandRetryOnInvalidInput() {
        Scanner scanner = createScannerWithInput("bad\n1\n");
        ConsoleWork ui = new ConsoleWork(scanner);
        assertTrue(ui.askHitOrStand());
    }

    @Test
    @DisplayName("Проверка продолжения игры на 2 раунд (покрытие roundNumber++)")
    void testStartMultipleRounds() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.TEN),

                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.TEN)
        );
        Scanner scanner = createScannerWithInput("0\n1\n0\n0\n");
        BlackjackGame game = new BlackjackGame(scanner, new TestDeck(cards));

        game.start();

        assertEquals(0, game.getPlayer().getScore());
        assertEquals(0, game.getDealer().getScore());
    }

    @Test
    @DisplayName("Проверка выбора количества колод меньше 1 (покрытие ветки decks < 1)")
    void testReadNumberOfDecksZeroOrNegative() {
        Scanner scanner = createScannerWithInput("0\n");
        ConsoleWork ui = new ConsoleWork(scanner);

        int result = ui.readNumberOfDecks();

        assertEquals(1, result);
    }
}