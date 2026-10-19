package ru.nsu.egorov.expression.operations;

import java.util.Map;
import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.expression.Number;

/**
 * Выражение, представляющее произведение двух подвыражений.
 */
public class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию умножения.
     *
     * @param left первый множитель
     * @param right второй множитель
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression sLeft = left.simplify();
        Expression sRight = right.simplify();

        if (sLeft instanceof Number && sRight instanceof Number) {
            return new Number(sLeft.eval(Map.of()) * sRight.eval(Map.of()));
        }

        if (isConstant(sLeft, 0) || isConstant(sRight, 0)) {
            return new Number(0);
        }

        if (isConstant(sLeft, 1)) {
            return sRight;
        }

        if (isConstant(sRight, 1)) {
            return sLeft;
        }

        return new Mul(sLeft, sRight);
    }

    private boolean isConstant(Expression expr, double value) {
        return expr instanceof Number num && num.getValue() == value;
    }
}