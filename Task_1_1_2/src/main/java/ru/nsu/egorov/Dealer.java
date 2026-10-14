package ru.nsu.egorov;

/**
 * Класс, представляющий дилера в Блэкджек.
 */
public class Dealer {
    private final Hand hand = new Hand();
    private int score = 0;

    /**
     * Возвращает текущую руку дилера с картами.
     *
     * @return Объект руки дилера.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Возвращает общее количество выигранных дилером раундов.
     *
     * @return Счет дилера.
     */
    public int getScore() {
        return score;
    }

    /**
     * Увеличивает счет побед дилера на единицу.
     */
    public void incrementScore() {
        this.score++;
    }

    /**
     * Определяет, должен ли дилер брать еще одну карту по правилам игры (если очков меньше 17).
     *
     * @return true, если дилеру необходимо взять карту; false в противном случае.
     */
    public boolean shouldHit() {
        return hand.getScore() < 17;
    }
}