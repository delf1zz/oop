package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.egorov.evaluation.Environment;

/**
 * Юнит-тесты для класса Environment.
 */
class EnvironmentTest {

    @Test
    @DisplayName("Парсинг строки окружения, включая битые пары")
    void testEnvironment() {
        Map<String, Double> map = Environment.parse("x = 5; invalid_pair; y = 10.5");
        assertEquals(2, map.size());
        assertEquals(5.0, map.get("x"));
        assertEquals(10.5, map.get("y"));

        assertTrue(Environment.parse(null).isEmpty());
        assertTrue(Environment.parse("").isEmpty());
    }
}