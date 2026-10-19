package ru.nsu.egorov.expression;

import java.util.Map;

/**
 * Выражение, представляющее переменную.
 */
public class Variable extends Expression {

    private final String name;

    /**
     * Создает переменную с заданным именем.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    public String getName() {
        return name;
    }

    /**
     * Вычисляет значение переменной из переданного контекста означивания.
     *
     * @param variables карта значений переменных
     * @return числовое значение переменной
     * @throws IllegalArgumentException если значение переменной не найдено в карте
     */
    @Override
    public double eval(Map<String, Double> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Не задано значение для переменной: " + name);
        }
        return variables.get(name);
    }

    /**
     * Возвращает производную переменной.
     *
     * @param variable имя переменной дифференцирования
     * @return число 1 или 0
     */
    @Override
    public Expression derivative(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    @Override
    public String print() {
        return name;
    }

    /**
     * Возвращает текущую переменную без изменений.
     *
     * @return текущий объект Variable
     */
    @Override
    public Expression simplify() {
        return this;
    }
}