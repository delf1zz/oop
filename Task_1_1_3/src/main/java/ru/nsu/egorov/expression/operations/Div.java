package ru.nsu.egorov.expression.operations;

import java.util.Map;
import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.expression.Number;

/**
 * Выражение, представляющее частное двух подвыражений.
 */
public class Div extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию деления.
     *
     * @param left делимое
     * @param right делитель
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(Map<String, Double> variables) {
        double denominator = right.eval(variables);
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return left.eval(variables) / denominator;
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    @Override
    public String print() {
        return "(" + left.print() + "/" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression sLeft = left.simplify();
        Expression sRight = right.simplify();

        if (sLeft instanceof Number && sRight instanceof Number) {
            return new Number(sLeft.eval(Map.of()) / sRight.eval(Map.of()));
        }

        if (isConstant(sLeft, 0)) {
            return new Number(0);
        }

        if (isConstant(sRight, 1)) {
            return sLeft;
        }

        if (sLeft.print().equals(sRight.print())) {
            return new Number(1);
        }

        return new Div(sLeft, sRight);
    }

    private boolean isConstant(Expression expr, double value) {
        return expr instanceof Number num && num.getValue() == value;
    }
}