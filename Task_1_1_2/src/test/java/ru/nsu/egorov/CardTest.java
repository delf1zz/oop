package ru.nsu.egorov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTest {

    @Test
    void testCardProperties() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.KING);
        assertEquals(Card.Suit.HEARTS, card.getSuit());
        assertEquals(Card.Rank.KING, card.getRank());
        assertEquals(10, card.getValue());
        assertFalse(card.isAce());
        assertEquals("Король Черви", card.toString());
    }

    @Test
    void testAceCard() {
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        assertEquals(11, card.getValue());
        assertTrue(card.isAce());
    }

    @Test
    void testEnumsCoverage() {
        for (Card.Suit suit : Card.Suit.values()) {
            assertNotNull(suit.getName());
            assertEquals(suit, Card.Suit.valueOf(suit.name()));
        }

        for (Card.Rank rank : Card.Rank.values()) {
            assertNotNull(rank.getName());
            assertTrue(rank.getValue() > 0);
            assertEquals(rank, Card.Rank.valueOf(rank.name()));
        }
    }
}