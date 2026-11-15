package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.expression.Number;
import ru.nsu.egorov.expression.Variable;
import ru.nsu.egorov.expression.operations.Add;
import ru.nsu.egorov.expression.operations.Div;
import ru.nsu.egorov.expression.operations.Mul;
import ru.nsu.egorov.expression.operations.Sub;

/**
 * Модульные тесты для иерархии выражений.
 */
class ExpressionTest {

    @Test
    @DisplayName("Тестирование Number")
    void testNumber() {
        Number numberInt = new Number(5);
        assertEquals(5.0, numberInt.eval(Map.of()));
        assertEquals(5.0, numberInt.eval(""));
        assertEquals(5.0, numberInt.getValue());
        assertEquals("5", numberInt.print());
        assertEquals("0", numberInt.derivative("x").print());
        assertTrue(numberInt.simplify() instanceof Number);

        Number numberFloat = new Number(5.5);
        assertEquals("5.5", numberFloat.print());
    }

    @Test
    @DisplayName("Тестирование Variable")
    void testVariable() {
        Variable v = new Variable("x");
        assertEquals("x", v.getName());
        assertEquals("x", v.print());
        assertEquals(10.0, v.eval(Map.of("x", 10.0)));
        assertEquals(10.0, v.eval("x = 10"));
        assertEquals("1", v.derivative("x").print());
        assertEquals("0", v.derivative("y").print());
        assertEquals(v, v.simplify());
        assertThrows(IllegalArgumentException.class, () -> v.eval(Map.of("y", 1.0)));
    }

    @Test
    @DisplayName("Тестирование Add")
    void testAdd() {
        Expression addNum = new Add(new Number(2), new Number(3));
        assertEquals("5", addNum.simplify().print());

        Expression addZeroLeft = new Add(new Number(0), new Variable("x"));
        assertEquals("x", addZeroLeft.simplify().print());

        Expression addZeroRight = new Add(new Variable("x"), new Number(0));
        assertEquals("x", addZeroRight.simplify().print());

        Expression mixedAdd = new Add(new Variable("x"), new Variable("y"));
        assertEquals("(x+y)", mixedAdd.simplify().print());
        assertEquals(5.0, mixedAdd.eval("x = 2; y = 3"));
        assertEquals("(1+0)", mixedAdd.derivative("x").print());
    }

    @Test
    @DisplayName("Тестирование Sub")
    void testSub() {
        Expression subNum = new Sub(new Number(5), new Number(2));
        assertEquals("3", subNum.simplify().print());

        Expression subSame = new Sub(new Variable("x"), new Variable("x"));
        assertEquals("0", subSame.simplify().print());

        Expression subZeroRight = new Sub(new Variable("x"), new Number(0));
        assertEquals("x", subZeroRight.simplify().print());

        Expression mixedSub = new Sub(new Variable("x"), new Variable("y"));
        assertEquals("(x-y)", mixedSub.simplify().print());
        assertEquals(4.0, mixedSub.eval("x = 7; y = 3"));
        assertEquals("(1-0)", mixedSub.derivative("x").print());
    }

    @Test
    @DisplayName("Тестирование Mul")
    void testMul() {
        Expression mul = new Mul(new Number(3), new Number(4));
        assertEquals(12.0, mul.eval(Map.of()));
        assertEquals("12", mul.simplify().print());

        assertEquals("0", new Mul(new Number(0), new Variable("x")).simplify().print());
        assertEquals("0", new Mul(new Variable("x"), new Number(0)).simplify().print());
        assertEquals("x", new Mul(new Number(1), new Variable("x")).simplify().print());
        assertEquals("x", new Mul(new Variable("x"), new Number(1)).simplify().print());

        Expression mulVars = new Mul(new Variable("x"), new Variable("y"));
        assertEquals("(x*y)", mulVars.simplify().print());
        assertEquals("((1*y)+(x*0))", mulVars.derivative("x").print());
    }

    @Test
    @DisplayName("Тестирование Div")
    void testDiv() {
        Expression divNum = new Div(new Number(10), new Number(2));
        assertEquals("5", divNum.simplify().print());

        Expression divZeroLeft = new Div(new Number(0), new Variable("x"));
        assertEquals("0", divZeroLeft.simplify().print());

        Expression divOneRight = new Div(new Variable("x"), new Number(1));
        assertEquals("x", divOneRight.simplify().print());

        Expression divSame = new Div(new Variable("x"), new Variable("x"));
        assertEquals("1", divSame.simplify().print());

        Expression mixedDiv = new Div(new Variable("x"), new Variable("y"));
        assertEquals("(x/y)", mixedDiv.simplify().print());
        assertEquals(2.0, mixedDiv.eval("x = 10; y = 5"));

        assertThrows(ArithmeticException.class, () -> new Div(
                new Number(5), new Number(0)).eval(Map.of()));
        assertEquals("(((1*y)-(x*0))/(y*y))", mixedDiv.derivative("x").print());
    }

    @Test
    @DisplayName("Покрытие веток isConstant и комбинированных instanceof")
    void testSimplifyCornerCases() {
        Expression numFive = new Number(5);
        Expression varX = new Variable("x");

        assertEquals("(5+x)", new Add(numFive, varX).simplify().print());
        assertEquals("(x+5)", new Add(varX, numFive).simplify().print());

        assertEquals("(5-x)", new Sub(numFive, varX).simplify().print());
        assertEquals("(x-5)", new Sub(varX, numFive).simplify().print());

        assertEquals("(5*x)", new Mul(numFive, varX).simplify().print());
        assertEquals("(x*5)", new Mul(varX, numFive).simplify().print());

        assertEquals("(5/x)", new Div(numFive, varX).simplify().print());
        assertEquals("(x/5)", new Div(varX, numFive).simplify().print());
    }
}