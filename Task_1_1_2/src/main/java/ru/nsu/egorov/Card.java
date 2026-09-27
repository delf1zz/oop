package ru.nsu.egorov;

/**
 * Класс, представляющий игральную карту с мастью и достоинством.
 */
public class Card {

    /**
     * Перечисление мастей карт.
     */
    public enum Suit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }

    /**
     * Перечисление достоинств карт и их базовой стоимости.
     */
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
        SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        /**
         * Возвращает базовое числовое значение карты.
         *
         * @return Значение карты в очках.
         */
        public int getValue() {
            return value;
        }
    }

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

    @Override
    public String toString() {
        return rank + " " + suit;
    }
}