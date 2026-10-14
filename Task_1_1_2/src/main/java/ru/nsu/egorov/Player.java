package ru.nsu.egorov;

/**
 * Класс, представляющий игрока в Блэкджек.
 */
public class Player {
    private final Hand hand = new Hand();
    private int score = 0;

    /**
     * Возвращает текущую руку игрока с картами.
     *
     * @return Объект руки игрока.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Возвращает общее количество выигранных игроком раундов.
     *
     * @return Счет игрока.
     */
    public int getScore() {
        return score;
    }

    /**
     * Увеличивает счет побед игрока на единицу.
     */
    public void incrementScore() {
        this.score++;
    }
}