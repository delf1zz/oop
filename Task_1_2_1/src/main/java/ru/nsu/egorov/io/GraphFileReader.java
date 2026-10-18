package ru.nsu.egorov.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import ru.nsu.egorov.Graph;

/**
 * Считыватель топологии графа из текстового файла.
 */
public class GraphFileReader {

    /**
     * Заполняет граф данными из указанного файла.
     * Первая строка: вершины через пробел.
     * Последующие строки: ребра вида "откуда куда".
     *
     * @param graph целевой граф
     * @param file файл топологии
     * @throws IOException при ошибках чтения
     */
    public void read(Graph graph, File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line == null) {
                return;
            }

            String[] vertices = line.trim().split("\\s+");
            for (String v : vertices) {
                if (!v.isEmpty()) {
                    graph.addVertex(v);
                }
            }

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    graph.addEdge(parts[0], parts[1]);
                }
            }
        }
    }
}