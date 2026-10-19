package ru.nsu.egorov.expression.operations;

import java.util.Map;
import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.expression.Number;

/**
 * Выражение, представляющее разность двух подвыражений.
 */
public class Sub extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию вычитания.
     *
     * @param left уменьшаемое
     * @param right вычитаемое
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) - right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression sLeft = left.simplify();
        Expression sRight = right.simplify();

        if (sLeft instanceof Number && sRight instanceof Number) {
            return new Number(sLeft.eval(Map.of()) - sRight.eval(Map.of()));
        }

        if (sLeft.print().equals(sRight.print())) {
            return new Number(0);
        }

        if (isConstant(sRight, 0)) {
            return sLeft;
        }

        return new Sub(sLeft, sRight);
    }

    private boolean isConstant(Expression expr, double value) {
        return expr instanceof Number num && num.getValue() == value;
    }
}