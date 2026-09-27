package ru.nsu.egorov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandTest {
    private Hand hand;

    @BeforeEach
    void setUp() {
        hand = new Hand();
    }

    @Test
    void testSimpleScore() {
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
        assertEquals(17, hand.getScore());
        assertEquals(2, hand.getCards().size());
    }

    @Test
    void testAceAsEleven() {
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        assertEquals(19, hand.getScore());
    }

    @Test
    void testAceAsOneOnBust() {
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        hand.addCard(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        assertEquals(21, hand.getScore());
        assertFalse(hand.isBust());
    }

    @Test
    void testBust() {
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.KING));
        hand.addCard(new Card(Card.Suit.CLUBS, Card.Rank.FIVE));
        assertEquals(25, hand.getScore());
        assertTrue(hand.isBust());
    }

    @Test
    void testBlackjackVariants() {
        // Честный блэкджек
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.KING));
        assertTrue(hand.isBlackjack());

        // 21 очко, но из 3 карт (не блэкджек)
        hand.clear();
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
        hand.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));
        assertFalse(hand.isBlackjack());

        // 2 карты, но не 21 очко (не блэкджек)
        hand.clear();
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.KING));
        assertFalse(hand.isBlackjack());
    }

    @Test
    void testClearHand() {
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        assertEquals(1, hand.getCards().size());
        hand.clear();
        assertEquals(0, hand.getCards().size());
        assertEquals(0, hand.getScore());
    }
}