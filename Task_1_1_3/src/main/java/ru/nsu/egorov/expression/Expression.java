package ru.nsu.egorov.expression;

import java.util.Map;

/**
 * Абстрактный корень иерархии математических выражений.
 */
public abstract class Expression {

    /**
     * Вычисляет значение выражения с учетом означивания переменных.
     *
     * @param variables карта значений переменных
     * @return результат вычисления
     */
    public abstract double eval(Map<String, Double> variables);

    /**
     * Вычисляет значение выражения по строке с означиваниями.
     *
     * @param assignment строка вида "x = 10; y = 13"
     * @return результат вычисления
     */
    public double eval(String assignment) {
        return eval(ru.nsu.egorov.evaluation.Environment.parse(assignment));
    }

    /**
     * Выполняет символьное дифференцирование выражения по заданной переменной.
     *
     * @param variable имя переменной дифференцирования
     * @return новое производное выражение
     */
    public abstract Expression derivative(String variable);

    /**
     * Возвращает строковое представление выражения в скобочной записи.
     *
     * @return строка выражения
     */
    public abstract String print();

    /**
     * Упрощает математическое выражение по базовым правилам.
     *
     * @return новое упрощенное выражение
     */
    public abstract Expression simplify();
}