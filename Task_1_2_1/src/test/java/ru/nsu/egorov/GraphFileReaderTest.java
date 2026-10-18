package ru.nsu.egorov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для модуля считывания графа из файла.
 */
class GraphFileReaderTest {

    @Test
    @DisplayName("Чтение корректного файла с графом")
    void testReadValidFile(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "graph.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("A B C\n");
            writer.write("A B\n");
            writer.write("B C\n");
        }

        Graph graph = new AdjacencyListGraph();
        graph.readFromFile(file);

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertFalse(graph.hasEdge("C", "A"));
    }

    @Test
    @DisplayName("Чтение пустого файла")
    void testReadEmptyFile(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "empty.txt");
        assertTrue(file.createNewFile());

        Graph graph = new AdjacencyMatrixGraph();
        graph.readFromFile(file);

        assertTrue(graph.getVertices().isEmpty());
    }

    @Test
    @DisplayName("Чтение файла с пустыми строками и неполными ребрами")
    void testReadFileWithEmptyLinesAndIncompleteEdges(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "sparse.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("A B C\n");
            writer.write("\n");
            writer.write("A B\n");
            writer.write("   \n");
            writer.write("SingleWord\n");
        }

        Graph graph = new AdjacencyListGraph();
        graph.readFromFile(file);

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.hasEdge("A", "B"));
    }

    @Test
    @DisplayName("Чтение файла с лишними пробелами между вершинами")
    void testReadFileWithExtraSpacesInFirstLine(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "spaces.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("A   B   C\n");
            writer.write("A B\n");
        }

        Graph graph = new AdjacencyListGraph();
        graph.readFromFile(file);

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.hasEdge("A", "B"));
    }

    @Test
    @DisplayName("Чтение файла с первой строкой из одних пробелов")
    void testReadFileWithSpacesOnlyFirstLine(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "spaces_only.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("    \n");
            writer.write("A B\n");
        }

        Graph graph = new AdjacencyListGraph();
        graph.readFromFile(file);

        assertTrue(graph.hasEdge("A", "B"));
    }
}