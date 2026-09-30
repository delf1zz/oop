package ru.nsu.egorov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий одну или несколько колод игральных карт.
 */
public class Deck {
    private final List<Card> cards;
    private final int numberOfDecks;

    /**
     * Создает одну стандартную колоду из 52 карт и перемешивает ее.
     */
    public Deck() {
        this(1);
    }

    /**
     * Создает указанное количество колод карт и перемешивает их.
     *
     * @param numberOfDecks Количество колод.
     */
    public Deck(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
        this.cards = new ArrayList<>();
        reset();
    }

    /**
     * Сбрасывает колоду до начального состояния (все колоды) и перемешивает её.
     */
    public final void reset() {
        cards.clear();
        for (int i = 0; i < numberOfDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
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