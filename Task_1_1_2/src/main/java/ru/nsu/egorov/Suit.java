package ru.nsu.egorov;

/**
 * Перечисление мастей карт в игре Блэкджек.
 */
public enum Suit {
    /**
     * Масть Пики.
     */
    SPADES("Пики"),

    /**
     * Масть Черви.
     */
    HEARTS("Черви"),

    /**
     * Масть Бубны.
     */
    DIAMONDS("Бубны"),

    /**
     * Масть Трефы.
     */
    CLUBS("Трефы");

    private final String name;

    /**
     * Конструктор масти.
     *
     * @param name Название масти на русском языке.
     */
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