package ru.nsu.egorov;

/**
 * Класс, представляющий игральную карту с мастью и достоинством.
 */
public class Card {

    private final Suit suit;
    private final Rank rank;

    /**
     * Конструктор карты.
     *
     * @param suit Масть карты.
     * @param rank Достоинство карты.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Возвращает масть карты.
     *
     * @return Масть.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Возвращает достоинство карты.
     *
     * @return Достоинство.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Возвращает базовую стоимость карты.
     *
     * @return Числовое значение очков.
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Проверяет, является ли карта тузом.
     *
     * @return true, если карта — туз, иначе false.
     */
    public boolean isAce() {
        return rank == Rank.ACE;
    }

    @Override
    public String toString() {
        return rank.getName() + " " + suit.getName();
    }
}