package ru.nsu.egorov.sort;

import java.util.List;
import ru.nsu.egorov.Graph;

/**
 * Стратегия топологической сортировки вершин ориентированного графа.
 */
public interface TopologicalSortStrategy {

    /**
     * Выполняет сортировку вершин переданного графа.
     *
     * @param graph граф для сортировки
     * @return список вершин в порядке топологической сортировки
     */
    List<String> sort(Graph graph);
}