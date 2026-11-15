package ru.nsu.egorov.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты поиска подстроки в файлах.
 */
class SubstringSearcherTest {

    @TempDir
    File tempDir;

    @Test
    @DisplayName("Пример из условия задачи")
    void testSampleInput() throws IOException {
        File file = new File(tempDir, "input.txt");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("абракадабра");
        }

        List<Long> actual = SubstringSearcher.find(file.getAbsolutePath(), "бра");
        assertEquals(List.of(1L, 8L), actual);
    }

    @Test
    @DisplayName("Поддержка разных символов UTF-8")
    void testUtf8Characters() throws IOException {
        File file = new File(tempDir, "utf8.txt");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("Привет 🌍 мир 🌍!");
        }

        List<Long> actual = SubstringSearcher.find(file.getAbsolutePath(), "🌍");
        assertEquals(List.of(7L, 14L), actual);
    }

    @Test
    @DisplayName("Поиск в большом сгенерированном файле")
    void testLargeGeneratedFile() throws IOException {
        File bigFile = new File(tempDir, "big.txt");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(bigFile, StandardCharsets.UTF_8))) {
            for (int i = 0; i < 1_000_000; i++) {
                writer.write("abcdefghij");
            }
            writer.write("needle");
            for (int i = 0; i < 100; i++) {
                writer.write("abcdefghij");
            }
        }

        List<Long> actual = SubstringSearcher.find(bigFile.getAbsolutePath(), "needle");
        assertEquals(List.of(10_000_000L), actual);
    }

    @Test
    @DisplayName("Исключение при отсутствии файла")
    void testMissingFile() {
        assertThrows(IOException.class, () ->
                SubstringSearcher.find("missing_file_for_test.txt", "abc"));
    }
}