package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testCardProperties() {
        Card card = new Card(Suit.HEARTS, Rank.KING);
        assertEquals(Suit.HEARTS, card.getSuit());
        assertEquals(Rank.KING, card.getRank());
        assertEquals(10, card.getValue());
        assertFalse(card.isAce());
        assertEquals("Король Черви", card.toString());
    }

    @Test
    void testAceCard() {
        Card card = new Card(Suit.SPADES, Rank.ACE);
        assertEquals(11, card.getValue());
        assertTrue(card.isAce());
    }

    @Test
    void testEnumsCoverage() {
        for (Suit suit : Suit.values()) {
            assertNotNull(suit.getName());
            assertEquals(suit, Suit.valueOf(suit.name()));
        }

        for (Rank rank : Rank.values()) {
            assertNotNull(rank.getName());
            assertTrue(rank.getValue() > 0);
            assertEquals(rank, Rank.valueOf(rank.name()));
        }
    }
}