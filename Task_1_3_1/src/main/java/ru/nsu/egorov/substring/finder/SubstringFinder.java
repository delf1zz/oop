package ru.nsu.egorov.substring.finder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Интерфейс для поиска всех вхождений подстроки в символьном потоке.
 */
public interface SubstringFinder {

    /**
     * Находит индексы начала всех вхождений подстроки в потоке.
     *
     * @param reader поток символов для поиска
     * @param target искомая подстрока
     * @return список индексов (начиная с 0)
     * @throws IOException при ошибке чтения из потока
     */
    List<Long> find(Reader reader, String target) throws IOException;
}