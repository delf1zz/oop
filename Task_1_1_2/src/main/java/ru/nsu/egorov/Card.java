package ru.nsu.egorov;

/**
 * Класс, представляющий игральную карту с мастью и достоинством.
 */
public class Card {

    /**
     * Перечисление мастей карт.
     */
    public enum Suit {
        SPADES("Пики"),
        HEARTS("Черви"),
        DIAMONDS("Бубны"),
        CLUBS("Трефы");

        private final String name;

        Suit(String name) {
            this.name = name;
        }

        /**
         * Возвращает название масти на русском языке.
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
        TWO("Двойка", 2),
        THREE("Тройка", 3),
        FOUR("Четверка", 4),
        FIVE("Пятерка", 5),
        SIX("Шестерка", 6),
        SEVEN("Семерка", 7),
        EIGHT("Восьмерка", 8),
        NINE("Девятка", 9),
        TEN("Десятка", 10),
        JACK("Валет", 10),
        QUEEN("Дама", 10),
        KING("Король", 10),
        ACE("Туз", 11);

        private final String name;
        private final int value;

        Rank(String name, int value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Возвращает название достоинства на русском языке.
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
        return rank.getName() + " " + suit.getName();
    }
}