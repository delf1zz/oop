package ru.nsu.egorov.substring.finder;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Поиск подстроки алгоритмом Кнута-Морриса-Пратта с буферизованным чтением.
 */
public class KmpSubstringFinder implements SubstringFinder {

    private static final int BUFFER_SIZE = 8192;

    @Override
    public List<Long> find(Reader reader, String target) throws IOException {
        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("Подстрока для поиска не может быть пустой");
        }
        if (reader == null) {
            throw new IllegalArgumentException("Поток чтения не должен быть null");
        }

        final int[] prefix = buildPrefix(target);
        final char[] buffer = new char[BUFFER_SIZE];
        final List<Long> occurrences = new ArrayList<>();
        int matched = 0;
        long index = 0;
        int read;

        while ((read = reader.read(buffer)) != -1) {
            for (int i = 0; i < read; i++) {
                char ch = buffer[i];

                while (matched > 0 && target.charAt(matched) != ch) {
                    matched = prefix[matched - 1];
                }
                if (target.charAt(matched) == ch) {
                    matched++;
                }
                if (matched == target.length()) {
                    occurrences.add(index - target.length() + 1);
                    matched = prefix[matched - 1];
                }
                index++;
            }
        }
        return occurrences;
    }

    /**
     * Строит массив префикс-функции для искомой строки.
     *
     * @param pattern искомая подстрока
     * @return массив длин наибольших собственных префиксов, совпадающих с суффиксами
     */
    private int[] buildPrefix(String pattern) {
        int[] prefix = new int[pattern.length()];
        for (int i = 1; i < pattern.length(); i++) {
            int j = prefix[i - 1];
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = prefix[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }
}