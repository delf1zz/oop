package ru.nsu.egorov;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import ru.nsu.egorov.io.GraphFileReader;
import ru.nsu.egorov.sort.DfsTopologicalSort;
import ru.nsu.egorov.sort.TopologicalSortStrategy;

/**
 * Базовый абстрактный класс графа, реализующий общую логику:
 * сравнение графов, хэширование, строковое представление, чтение и сортировку.
 */
public abstract class AbstractGraph implements Graph {

    private GraphFileReader fileReader = new GraphFileReader();
    private TopologicalSortStrategy defaultSortStrategy = new DfsTopologicalSort();

    /**
     * Считывает граф из файла с помощью внутреннего считывателя.
     *
     * @param file файл для чтения
     * @throws IOException при ошибках чтения
     */
    @Override
    public void readFromFile(File file) throws IOException {
        fileReader.read(this, file);
    }

    /**
     * Сортирует граф стандартным алгоритмом на основе DFS.
     *
     * @return отсортированный список вершин
     */
    @Override
    public List<String> topologicalSort() {
        return topologicalSort(defaultSortStrategy);
    }

    /**
     * Сортирует граф переданным алгоритмом стратегии.
     *
     * @param strategy стратегия сортировки
     * @return отсортированный список вершин
     * @throws NullPointerException если strategy равна null
     */
    @Override
    public List<String> topologicalSort(TopologicalSortStrategy strategy) {
        Objects.requireNonNull(strategy, "Стратегия сортировки не может быть null");
        return strategy.sort(this);
    }

    /**
     * Проверяет эквивалентность двух графов независимо от их внутреннего представления.
     *
     * @param obj объект для сравнения
     * @return true, если графы имеют одинаковый набор вершин и одинаковые связи
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph other)) {
            return false;
        }

        Set<String> thisVertices = new HashSet<>(this.getVertices());
        Set<String> otherVertices = new HashSet<>(other.getVertices());
        if (!thisVertices.equals(otherVertices)) {
            return false;
        }

        for (String v : thisVertices) {
            Set<String> thisNeighbors = new HashSet<>(this.getNeighbors(v));
            Set<String> otherNeighbors = new HashSet<>(other.getNeighbors(v));
            if (!thisNeighbors.equals(otherNeighbors)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Вычисляет хэш-код графа на основе его вершин и соседей.
     *
     * @return хэш-код структуры
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (String v : getVertices()) {
            result += Objects.hash(v, new HashSet<>(getNeighbors(v)));
        }
        return result;
    }

    /**
     * Формирует строковое представление графа со списками смежности.
     *
     * @return текстовое описание графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String v : getVertices()) {
            sb.append(v).append(" -> ").append(getNeighbors(v)).append("\n");
        }
        return sb.toString();
    }
}