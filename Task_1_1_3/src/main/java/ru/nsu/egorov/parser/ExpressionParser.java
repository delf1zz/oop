package ru.nsu.egorov.parser;

import ru.nsu.egorov.expression.Expression;
import ru.nsu.egorov.expression.Number;
import ru.nsu.egorov.expression.Variable;
import ru.nsu.egorov.expression.operations.Add;
import ru.nsu.egorov.expression.operations.Div;
import ru.nsu.egorov.expression.operations.Mul;
import ru.nsu.egorov.expression.operations.Sub;

/**
 * Синтаксический анализатор для разбора математических выражений.
 */
public class ExpressionParser {

    private int pos = 0;
    private String src;

    /**
     * Конструктор парсера выражений по умолчанию.
     */
    public ExpressionParser() {
    }

    /**
     * Разбирает строковое представление в иерархию объектов Expression.
     *
     * @param str входная строка выражения
     * @return разобранное выражение
     */
    public Expression parse(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("Входная строка не может быть пустой");
        }
        this.src = str.replaceAll("\\s+", "");
        this.pos = 0;
        Expression result = parseExpression();
        if (pos < src.length()) {
            throw new IllegalArgumentException("Лишние символы в конце строки: " + src.substring(pos));
        }
        return result;
    }

    private Expression parseExpression() {
        if (pos >= src.length()) {
            throw new IllegalArgumentException("Неожиданный конец строки");
        }

        char ch = src.charAt(pos);
        if (ch == '(') {
            pos++;
            Expression left = parseExpression();

            if (pos >= src.length()) {
                throw new IllegalArgumentException("Ожидался бинарный оператор");
            }
            char op = src.charAt(pos++);

            Expression right = parseExpression();

            if (pos >= src.length() || src.charAt(pos) != ')') {
                throw new IllegalArgumentException("Ожидалась закрывающая скобка ')'");
            }
            pos++;

            return switch (op) {
                case '+' -> new Add(left, right);
                case '-' -> new Sub(left, right);
                case '*' -> new Mul(left, right);
                case '/' -> new Div(left, right);
                default -> throw new IllegalArgumentException("Неизвестный оператор: " + op);
            };
        }

        int start = pos;
        while (pos < src.length()) {
            char c = src.charAt(pos);
            if (Character.isLetterOrDigit(c) || c == '.') {
                pos++;
            } else {
                break;
            }
        }

        if (start == pos) {
            throw new IllegalArgumentException("Ожидался идентификатор или число на позиции " + pos);
        }

        String token = src.substring(start, pos);
        try {
            return new Number(Double.parseDouble(token));
        } catch (NumberFormatException e) {
            return new Variable(token);
        }
    }
}