package ru.nsu.egorov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий руку игрока или дилера в игре Блэкджек.
 */
public class Hand {
    private final List<Card> cards;

    /**
     * Создает пустую руку.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card Карта для добавления.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Возвращает неизменяемый список карт в руке.
     *
     * @return Список карт.
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Подсчитывает суммарное количество очков в руке с учетом тузов.
     *
     * @return Сумма очков.
     */
    public int getScore() {
        int score = 0;
        int aces = 0;

        for (Card card : cards) {
            score += card.getValue();
            if (card.getRank() == Card.Rank.ACE) {
                aces++;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }

    /**
     * Проверяет, перебрал ли игрок больше 21 очка.
     *
     * @return true, если сумма очков больше 21, иначе false.
     */
    public boolean isBust() {
        return getScore() > 21;
    }

    /**
     * Проверяет, собрал ли игрок "Блэкджек" (21 очко с первых двух карт).
     *
     * @return true, если в руке ровно 2 карты и 21 очко, иначе false.
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }

    /**
     * Очищает руку от карт.
     */
    public void clear() {
        cards.clear();
    }
}