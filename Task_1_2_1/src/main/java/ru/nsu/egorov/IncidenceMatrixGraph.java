package ru.nsu.egorov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Реализация ориентированного графа на основе матрицы инцидентности.
 * Строки представляют вершины, столбцы — ориентированные ребра.
 * Кодирование: 1 — начало ребра, -1 — конец ребра, 2 — петля.
 */
public class IncidenceMatrixGraph extends AbstractGraph {

    private List<String> vertices = new ArrayList<>();
    private List<List<Integer>> matrix = new ArrayList<>();
    private int edgeCount = 0;

    /**
     * Добавляет строку для новой вершины с нулевыми значениями для всех существующих ребер.
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
        matrix.add(new ArrayList<>(Collections.nCopies(edgeCount, 0)));
        return true;
    }

    /**
     * Удаляет вершину и все столбцы ребер, в которых она участвовала.
     *
     * @param vertex имя удаляемой вершины
     * @return true, если вершина найдена и удалена
     */
    @Override
    public boolean removeVertex(String vertex) {
        int vIndex = vertices.indexOf(vertex);
        if (vIndex == -1) {
            return false;
        }

        List<Integer> edgesToRemove = new ArrayList<>();
        for (int e = 0; e < edgeCount; e++) {
            if (matrix.get(vIndex).get(e) != 0) {
                edgesToRemove.add(e);
            }
        }

        for (int i = edgesToRemove.size() - 1; i >= 0; i--) {
            removeEdgeByIndex(edgesToRemove.get(i));
        }

        matrix.remove(vIndex);
        vertices.remove(vIndex);
        return true;
    }

    /**
     * Добавляет новый столбец инцидентности для ребра.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро добавлено
     */
    @Override
    public boolean addEdge(String from, String to) {
        addVertex(from);
        addVertex(to);
        if (hasEdge(from, to)) {
            return false;
        }

        int fromIdx = vertices.indexOf(from);
        int toIdx = vertices.indexOf(to);

        for (int v = 0; v < vertices.size(); v++) {
            int value = 0;
            if (fromIdx == toIdx && v == fromIdx) {
                value = 2;
            } else if (v == fromIdx) {
                value = 1;
            } else if (v == toIdx) {
                value = -1;
            }
            matrix.get(v).add(value);
        }

        edgeCount++;
        return true;
    }

    /**
     * Находит и удаляет столбец соответствующего ребра.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро найдено и удалено
     */
    @Override
    public boolean removeEdge(String from, String to) {
        int fromIdx = vertices.indexOf(from);
        int toIdx = vertices.indexOf(to);
        if (fromIdx == -1 || toIdx == -1) {
            return false;
        }

        int edgeIdx = findEdgeIndex(fromIdx, toIdx);
        if (edgeIdx == -1) {
            return false;
        }

        removeEdgeByIndex(edgeIdx);
        return true;
    }

    /**
     * Ищет индекс столбца ребра по индексам его начальной и конечной вершин.
     */
    private int findEdgeIndex(int fromIdx, int toIdx) {
        for (int e = 0; e < edgeCount; e++) {
            if (fromIdx == toIdx) {
                if (matrix.get(fromIdx).get(e) == 2) {
                    return e;
                }
            } else {
                if (matrix.get(fromIdx).get(e) == 1 && matrix.get(toIdx).get(e) == -1) {
                    return e;
                }
            }
        }
        return -1;
    }

    /**
     * Удаляет столбец ребра по индексу из всех строк матрицы.
     */
    private void removeEdgeByIndex(int edgeIdx) {
        for (List<Integer> row : matrix) {
            row.remove(edgeIdx);
        }
        edgeCount--;
    }

    /**
     * Определяет исходящих соседей, анализируя столбцы со значением 1 или 2 в строке вершины.
     *
     * @param vertex имя вершины
     * @return список исходящих смежных вершин
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        int fromIdx = vertices.indexOf(vertex);
        if (fromIdx == -1) {
            return Collections.emptyList();
        }

        List<String> neighbors = new ArrayList<>();
        for (int e = 0; e < edgeCount; e++) {
            int val = matrix.get(fromIdx).get(e);
            if (val == 2) {
                neighbors.add(vertex);
            } else if (val == 1) {
                for (int v = 0; v < vertices.size(); v++) {
                    if (matrix.get(v).get(e) == -1) {
                        neighbors.add(vertices.get(v));
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Возвращает список всех вершин.
     *
     * @return список вершин
     */
    @Override
    public List<String> getVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * Проверяет существование столбца, соединяющего указанные вершины.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если столбец ребра существует
     */
    @Override
    public boolean hasEdge(String from, String to) {
        int fromIdx = vertices.indexOf(from);
        int toIdx = vertices.indexOf(to);
        if (fromIdx == -1 || toIdx == -1) {
            return false;
        }
        return findEdgeIndex(fromIdx, toIdx) != -1;
    }
}