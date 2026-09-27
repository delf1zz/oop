package ru.nsu.egorov;

/**
 * Класс, представляющий игральную карту с мастью и достоинством.
 */
public class Card {

    /**
     * Перечисление мастей карт.
     */
    public enum Suit {
        SPADES("Spades"),
        HEARTS("Hearts"),
        DIAMONDS("Diamonds"),
        CLUBS("Clubs");

        private final String name;

        Suit(String name) {
            this.name = name;
        }

        /**
         * Возвращает название масти.
         *
         * @return Название масти.
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Перечисление достоинств карт и их базовой стоимости.
     */
    public enum Rank {
        TWO("Two", 2), THREE("Three", 3), FOUR("Four", 4), FIVE("Five", 5), SIX("Six", 6),
        SEVEN("Seven", 7), EIGHT("Eight", 8), NINE("Nine", 9), TEN("Ten", 10),
        JACK("Jack", 10), QUEEN("Queen", 10), KING("King", 10), ACE("Ace", 11);

        private final String name;
        private final int value;

        Rank(String name, int value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Возвращает название достоинства.
         *
         * @return Название достоинства.
         */
        public String getName() {
            return name;
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
        return rank + " " + suit;
    }
}