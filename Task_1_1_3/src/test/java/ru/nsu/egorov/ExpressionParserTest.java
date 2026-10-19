package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.parser.ExpressionParser;

/**
 * Юнит-тесты для синтаксического анализатора ExpressionParser.
 */
class ExpressionParserTest {

    @Test
    @DisplayName("Успешный парсинг всех операторов, переменных, целых чисел и чисел с точкой")
    void testValidExpressions() {
        ExpressionParser parser = new ExpressionParser();

        Expression parsedAdd = parser.parse("(x+2)");
        assertEquals("(x+2)", parsedAdd.print());

        Expression parsedSub = parser.parse("(5-3)");
        assertEquals("(5-3)", parsedSub.print());

        Expression parsedMul = parser.parse("(2*y)");
        assertEquals("(2*y)", parsedMul.print());

        Expression parsedDiv = parser.parse("(8/4)");
        assertEquals("(8/4)", parsedDiv.print());

        Expression parsedFloat = parser.parse("(x+3.14)");
        assertEquals("(x+3.14)", parsedFloat.print());

        assertEquals("42", parser.parse("42").print());
        assertEquals("varName", parser.parse("varName").print());
    }

    @Test
    @DisplayName("Покрытие ошибок валидации входной строки")
    void testBlankAndNullInputs() {
        ExpressionParser parser = new ExpressionParser();

        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("   "));
    }

    @Test
    @DisplayName("Покрытие синтаксических ошибок в скобочных выражениях")
    void testParenthesesAndOperatorErrors() {
        ExpressionParser parser = new ExpressionParser();

        assertThrows(IllegalArgumentException.class, () -> parser.parse("(1?2)"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("("));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(1"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(1+2"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(1+2]"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(1+2)extra"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(+2)"));
    }
}