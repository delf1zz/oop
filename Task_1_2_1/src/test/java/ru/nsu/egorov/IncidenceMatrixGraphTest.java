package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Модульные тесты для реализации ориентированного графа на основе матрицы инцидентности.
 */
class IncidenceMatrixGraphTest extends BaseGraphTest {

    @Override
    protected Graph createGraph() {
        return new IncidenceMatrixGraph();
    }

    @Test
    @DisplayName("Удаление вершины при наличии несвязанных ребер и петель")
    void testRemoveVertexWithUnrelatedEdgesAndLoops() {
        graph.addEdge("A", "A");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "C");

        assertEquals(List.of("C"), graph.getNeighbors("B"));

        assertTrue(graph.removeVertex("A"));
        assertFalse(graph.hasEdge("A", "A"));
        assertFalse(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "C"));
        assertEquals(Collections.emptyList(), graph.getNeighbors("UNKNOWN"));
    }

    @Test
    @DisplayName("Поиск ребра и соседей при наличии других различных ребер")
    void testFindEdgeAndNeighborsWithMultipleEdges() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "A");
        graph.addEdge("C", "C");

        assertFalse(graph.hasEdge("A", "A"));
        assertFalse(graph.hasEdge("A", "C"));
        assertFalse(graph.hasEdge("C", "A"));

        graph.addVertex("D");
        assertTrue(graph.getNeighbors("D").isEmpty());
    }

    @Test
    @DisplayName("Поиск соседей, когда сток ребра находится на последней вершине")
    void testGetNeighborsWhenTargetIsLastVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("Z");

        graph.addEdge("A", "Z");

        assertEquals(List.of("Z"), graph.getNeighbors("A"));
    }
}