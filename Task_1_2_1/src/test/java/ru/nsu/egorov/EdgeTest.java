package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.egorov.edge.Edge;

/**
 * Тесты для неизменяемой записи Edge.
 */
class EdgeTest {

    @Test
    @DisplayName("Корректное создание и свойства рекорда Edge")
    void testEdgeRecord() {
        Edge edge1 = new Edge("A", "B");
        Edge edge2 = new Edge("A", "B");
        Edge edge3 = new Edge("B", "A");

        assertEquals("A", edge1.from());
        assertEquals("B", edge1.to());
        assertEquals(edge1, edge2);
        assertNotEquals(edge1, edge3);
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    @DisplayName("Валидация null аргументов в Edge")
    void testEdgeNullArguments() {
        assertThrows(NullPointerException.class, () -> new Edge(null, "B"));
        assertThrows(NullPointerException.class, () -> new Edge("A", null));
    }
}