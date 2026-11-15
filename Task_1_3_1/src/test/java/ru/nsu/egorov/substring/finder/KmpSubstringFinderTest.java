package ru.nsu.egorov.substring.finder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Модульные тесты для алгоритма КМП.
 */
class KmpSubstringFinderTest {

    private final SubstringFinder finder = new KmpSubstringFinder();

    @Test
    @DisplayName("Перекрывающиеся вхождения подстроки")
    void testOverlappingMatches() throws IOException {
        try (StringReader reader = new StringReader("абаба")) {
            List<Long> actual = finder.find(reader, "аба");
            assertEquals(List.of(0L, 2L), actual);
        }
    }

    @Test
    @DisplayName("Шаблон с повторяющимися префиксами и откатами")
    void testRepeatedPrefixes() throws IOException {
        try (StringReader reader = new StringReader("abacabacabad")) {
            List<Long> actual = finder.find(reader, "abacabad");
            assertEquals(List.of(4L), actual);
        }
    }

    @Test
    @DisplayName("Отсутствие совпадений и пустой входной поток")
    void testNoMatchesAndEmptyInput() throws IOException {
        try (StringReader reader = new StringReader("обычный текст")) {
            assertEquals(List.of(), finder.find(reader, "нет"));
        }

        try (StringReader emptyReader = new StringReader("")) {
            assertEquals(List.of(), finder.find(emptyReader, "тест"));
        }
    }

    @Test
    @DisplayName("Обработка некорректных аргументов")
    void testInvalidArguments() {
        try (StringReader reader = new StringReader("строка")) {
            assertThrows(IllegalArgumentException.class, () -> finder.find(reader, ""));
            assertThrows(IllegalArgumentException.class, () -> finder.find(reader, null));
        }
        assertThrows(IllegalArgumentException.class, () -> finder.find(null, "строка"));
    }
}