package ru.nsu.egorov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Реализация ориентированного графа на основе матрицы смежности (таблицы истинности связей).
 */
public class AdjacencyMatrixGraph extends AbstractGraph {

    private List<String> vertices = new ArrayList<>();
    private List<List<Boolean>> matrix = new ArrayList<>();

    /**
     * Добавляет новую вершину и расширяет строки и столбцы матрицы смежности.
     *
     * @param vertex имя вершины
     * @return true, если вершина добавлена впервые
     */
    @Override
    public boolean addVertex(String vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        for (List<Boolean> row : matrix) {
            row.add(false);
        }
        List<Boolean> newRow = new ArrayList<>(Collections.nCopies(vertices.size(), false));
        matrix.add(newRow);
        return true;
    }

    /**
     * Удаляет вершину, а также соответствующую строку и столбец в матрице.
     *
     * @param vertex имя удаляемой вершины
     * @return true, если вершина найдена и удалена
     */
    @Override
    public boolean removeVertex(String vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            return false;
        }
        vertices.remove(index);
        matrix.remove(index);
        for (List<Boolean> row : matrix) {
            row.remove(index);
        }
        return true;
    }

    /**
     * Устанавливает флаг true в ячейке [fromIdx][toIdx] матрицы.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро добавлено
     */
    @Override
    public boolean addEdge(String from, String to) {
        addVertex(from);
        addVertex(to);
        int fromIdx = vertices.indexOf(from);
        int toIdx = vertices.indexOf(to);
        if (matrix.get(fromIdx).get(toIdx)) {
            return false;
        }
        matrix.get(fromIdx).set(toIdx, true);
        return true;
    }

    /**
     * Сбрасывает флаг в ячейке матрицы на false.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро существовало и сброшено
     */
    @Override
    public boolean removeEdge(String from, String to) {
        int fromIdx = vertices.indexOf(from);
        int toIdx = vertices.indexOf(to);
        if (fromIdx == -1 || toIdx == -1 || !matrix.get(fromIdx).get(toIdx)) {
            return false;
        }
        matrix.get(fromIdx).set(toIdx, false);
        return true;
    }

    /**
     * Извлекает вершины, для которых в строке вершины установлен флаг true.
     *
     * @param vertex имя вершины
     * @return список исходящих соседей
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            return Collections.emptyList();
        }
        List<String> neighbors = new ArrayList<>();
        List<Boolean> row = matrix.get(index);
        for (int i = 0; i < vertices.size(); i++) {
            if (row.get(i)) {
                neighbors.add(vertices.get(i));
            }
        }
        return neighbors;
    }

    /**
     * Возвращает копию списка вершин графа.
     *
     * @return список вершин
     */
    @Override
    public List<String> getVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * Проверяет значение в ячейке матрицы [fromIdx][toIdx].
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро присутствует
     */
    @Override
    public boolean hasEdge(String from, String to) {
        int fromIdx = vertices.indexOf(from);
        int toIdx = vertices.indexOf(to);
        if (fromIdx == -1 || toIdx == -1) {
            return false;
        }
        return matrix.get(fromIdx).get(toIdx);
    }
}