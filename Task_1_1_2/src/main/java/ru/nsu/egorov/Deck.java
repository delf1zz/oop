package ru.nsu.egorov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий колоду из 52 игральных карт.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Создает новую колоду карт и перемешивает ее.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        reset();
    }

    /**
     * Сбрасывает колоду до начального состояния (52 карты) и перемешивает её.
     */
    public final void reset() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Вытягивает одну карту из колоды. Если колода пуста, автоматически пересоздает ее.
     *
     * @return Вытянутая карта.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            reset();
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Возвращает количество оставшихся карт в колоде.
     *
     * @return Количество карт.
     */
    public int size() {
        return cards.size();
    }

    /**
     * Возвращает количество оставшихся карт в колоде (синоним size).
     *
     * @return Количество карт.
     */
    public int remainingCards() {
        return size();
    }
}