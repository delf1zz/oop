package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.egorov.exception.CycleDetectionException;
import ru.nsu.egorov.sort.DfsTopologicalSort;

/**
 * Тесты алгоритма топологической сортировки DFS.
 */
class DfsTopologicalSortTest {

    @Test
    @DisplayName("Сортировка ациклического ориентированного графа (DAG)")
    void testValidDagSort() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> order = graph.topologicalSort(new DfsTopologicalSort());

        assertEquals(4, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("A") < order.indexOf("C"));
        assertTrue(order.indexOf("B") < order.indexOf("D"));
        assertTrue(order.indexOf("C") < order.indexOf("D"));
    }

    @Test
    @DisplayName("Обнаружение простого цикла выбрасывает CycleDetectionException")
    void testCycleDetection() {
        Graph graph = new AdjacencyMatrixGraph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertThrows(CycleDetectionException.class, graph::topologicalSort);
    }

    @Test
    @DisplayName("Обнаружение петли как цикла")
    void testSelfLoopCycle() {
        Graph graph = new IncidenceMatrixGraph();
        graph.addEdge("A", "A");

        assertThrows(CycleDetectionException.class, graph::topologicalSort);
    }
}