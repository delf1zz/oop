package ru.nsu.egorov.substring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import ru.nsu.egorov.substring.finder.KmpSubstringFinder;
import ru.nsu.egorov.substring.finder.SubstringFinder;

/**
 * Класс для поиска вхождений подстроки в файле в кодировке UTF-8.
 */
public final class SubstringSearcher {

    private SubstringSearcher() {
    }

    /**
     * Ищет все вхождения подстроки в указанном файле.
     *
     * @param filename имя или путь к файлу
     * @param target искомая подстрока
     * @return список позиций начала каждого вхождения
     * @throws IOException если не удалось открыть или прочитать файл
     */
    public static List<Long> find(String filename, String target) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            SubstringFinder finder = new KmpSubstringFinder();
            return finder.find(reader, target);
        }
    }
}