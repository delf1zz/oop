package ru.nsu.egorov.evaluation;

import java.util.HashMap;
import java.util.Map;

/**
 * Утилитный класс для работы с контекстом (означиванием) переменных.
 * Предназначен для разбора строки присваивания значений переменным.
 */
public final class Environment {

    /**
     * Приватный конструктор для предотвращения создания экземпляров утилитного класса.
     */
    private Environment() {}

    /**
     * Парсит строку вида "x = 10; y = 13" в карту значений переменных.
     *
     * @param assignment строка означивания переменных, разделенных точкой с запятой
     * @return карта, сопоставляющая имена переменных их числовым значениям
     */
    public static Map<String, Double> parse(String assignment) {
        Map<String, Double> variables = new HashMap<>();
        if (assignment == null || assignment.isBlank()) {
            return variables;
        }

        String[] pairs = assignment.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                double value = Double.parseDouble(keyValue[1].trim());
                variables.put(key, value);
            }
        }
        return variables;
    }
}