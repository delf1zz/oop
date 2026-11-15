package ru.nsu.egorov.expression.operations;

import java.util.Map;
import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.expression.Number;

/**
 * Выражение, представляющее сумму двух подвыражений.
 */
public class Add extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию сложения.
     *
     * @param left левое подвыражение
     * @param right правое подвыражение
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String print() {
        return "(" + left.print() + "+" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(simplifiedLeft.eval(Map.of()) + simplifiedRight.eval(Map.of()));
        }

        if (isConstant(simplifiedLeft, 0)) {
            return simplifiedRight;
        }

        if (isConstant(simplifiedRight, 0)) {
            return simplifiedLeft;
        }

        return new Add(simplifiedLeft, simplifiedRight);
    }

    private boolean isConstant(Expression expr, double value) {
        return expr instanceof Number num && num.getValue() == value;
    }
}