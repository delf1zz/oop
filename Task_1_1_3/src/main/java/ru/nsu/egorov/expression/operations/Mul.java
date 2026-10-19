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
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(simplifiedLeft.eval(Map.of()) * simplifiedRight.eval(Map.of()));
        }

        if (isConstant(simplifiedLeft, 0) || isConstant(simplifiedRight, 0)) {
            return new Number(0);
        }

        if (isConstant(simplifiedLeft, 1)) {
            return simplifiedRight;
        }

        if (isConstant(simplifiedRight, 1)) {
            return simplifiedLeft;
        }

        return new Mul(simplifiedLeft, simplifiedRight);
    }

    private boolean isConstant(Expression expr, double value) {
        return expr instanceof Number num && num.getValue() == value;
    }
}