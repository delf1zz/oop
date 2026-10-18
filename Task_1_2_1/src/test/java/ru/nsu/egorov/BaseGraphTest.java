package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Базовый абстрактный класс с тестами общих операций над графом.
 */
public abstract class BaseGraphTest {

    protected Graph graph;

    /**
     * Фабричный метод для создания экземпляра тестируемого графа.
     *
     * @return новый экземпляр Graph
     */
    protected abstract Graph createGraph();

    @BeforeEach
    void setUp() {
        graph = createGraph();
    }

    @Test
    @DisplayName("Добавление и удаление вершин")
    void testVertexLifecycle() {
        assertTrue(graph.addVertex("A"));
        assertFalse(graph.addVertex("A"));
        assertTrue(graph.getVertices().contains("A"));

        assertTrue(graph.removeVertex("A"));
        assertFalse(graph.removeVertex("A"));
        assertFalse(graph.getVertices().contains("A"));
        assertFalse(graph.removeVertex("UNKNOWN"));
    }

    @Test
    @DisplayName("Операции с ребрами и соседями")
    void testEdgeLifecycle() {
        assertTrue(graph.addEdge("A", "B"));
        assertFalse(graph.addEdge("A", "B"));
        assertTrue(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));
        assertFalse(graph.hasEdge("A", "C"));

        assertEquals(List.of("B"), graph.getNeighbors("A"));
        assertEquals(Collections.emptyList(), graph.getNeighbors("B"));
        assertEquals(Collections.emptyList(), graph.getNeighbors("NOT_EXISTS"));

        assertTrue(graph.removeEdge("A", "B"));
        assertFalse(graph.removeEdge("A", "B"));
        assertFalse(graph.removeEdge("X", "Y"));
        assertFalse(graph.hasEdge("A", "B"));
    }

    @Test
    @DisplayName("Удаление вершины очищает инцидентные ребра")
    void testCascadeEdgeRemovalOnVertexDelete() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertTrue(graph.removeVertex("B"));
        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));
        assertEquals(Collections.emptyList(), graph.getNeighbors("A"));
    }

    @Test
    @DisplayName("Поддержка петель")
    void testSelfLoop() {
        assertTrue(graph.addEdge("A", "A"));
        assertTrue(graph.hasEdge("A", "A"));
        assertEquals(List.of("A"), graph.getNeighbors("A"));

        assertTrue(graph.removeEdge("A", "A"));
        assertFalse(graph.hasEdge("A", "A"));
    }

    @Test
    @DisplayName("Удаление ребра, если одна из вершин не существует или ребра нет")
    void testRemoveNonExistentEdgeCases() {
        graph.addVertex("A");
        graph.addVertex("B");

        assertFalse(graph.removeEdge("A", "B"));

        assertFalse(graph.removeEdge("A", "UNKNOWN"));

        assertFalse(graph.removeEdge("UNKNOWN", "B"));

        assertFalse(graph.removeEdge("X", "Y"));

        assertFalse(graph.hasEdge("A", "UNKNOWN"));
        assertFalse(graph.hasEdge("UNKNOWN", "B"));
    }
}