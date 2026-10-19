package ru.nsu.egorov.expression;

import java.util.Map;

/**
 * Выражение, представляющее числовую константу.
 */
public class Number extends Expression {

    private final double value;

    /**
     * Создает числовую константу.
     *
     * @param value значение числа
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Возвращает числовое значение.
     *
     * @return значение константы
     */
    public double getValue() {
        return value;
    }

    /**
     * Возвращает значение числовой константы независимо от контекста переменных.
     *
     * @param variables карта значений переменных (не используется)
     * @return числовое значение константы
     */
    @Override
    public double eval(Map<String, Double> variables) {
        return value;
    }

    /**
     * Возвращает производную константы по любой переменной, равную нулю.
     *
     * @param variable имя переменной дифференцирования
     * @return константа 0
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Возвращает строковое представление числа (без дробной части, если оно целое).
     *
     * @return строка с числом
     */
    @Override
    public String print() {
        if (value == (int) value) {
            return String.valueOf((int) value);
        }
        return String.valueOf(value);
    }

    /**
     * Возвращает текущую константу, так как она не требует упрощения.
     *
     * @return текущий объект Number
     */
    @Override
    public Expression simplify() {
        return this;
    }
}