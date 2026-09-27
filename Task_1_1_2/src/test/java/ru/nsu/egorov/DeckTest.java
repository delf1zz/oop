package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void testDeckInitialization() {
        Deck deck = new Deck();
        assertEquals(52, deck.remainingCards());
    }

    @Test
    void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.remainingCards());
    }

    @Test
    void testAutoResetWhenEmpty() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }
        assertEquals(0, deck.remainingCards());

        // Вызов при пустой колоде вызывает reset()
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.remainingCards());
    }
}