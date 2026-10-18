package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AbstractGraphCommonTest {

    @Test
    @DisplayName("Сравнение разных реализаций графа на равенство и краевые ветки equals")
    void testCrossImplementationEquality() {
        Graph g1 = new AdjacencyListGraph();
        Graph g2 = new AdjacencyMatrixGraph();
        Graph g3 = new IncidenceMatrixGraph();

        for (Graph g : List.of(g1, g2, g3)) {
            g.addEdge("1", "2");
            g.addEdge("2", "3");
            g.addVertex("4");
        }

        assertEquals(g1, g1);

        assertEquals(g1, g2);
        assertEquals(g2, g3);
        assertEquals(g1.hashCode(), g2.hashCode());
        assertEquals(g2.hashCode(), g3.hashCode());

        g1.addEdge("1", "4");
        assertNotEquals(g1, g2);

        Graph gDifferentVertices = new AdjacencyListGraph();
        gDifferentVertices.addVertex("999");
        assertNotEquals(g2, gDifferentVertices);

        assertNotEquals(g1, null);
        assertNotEquals(g1, "Не граф");
    }

    @Test
    @DisplayName("Проверка дефолтной сортировки topologicalSort() и валидации")
    void testTopologicalSortDefaultAndToString() {
        Graph graph = new AdjacencyListGraph();
        graph.addEdge("A", "B");

        List<String> order = graph.topologicalSort();
        assertEquals(List.of("A", "B"), order);

        assertTrue(graph.toString().contains("A -> [B]"));
        assertThrows(NullPointerException.class, () -> graph.topologicalSort(null));
    }
}