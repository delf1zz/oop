package ru.nsu.egorov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация ориентированного графа на основе списков смежности (Map списков).
 */
public class AdjacencyListGraph extends AbstractGraph {

    private Map<String, List<String>> adjList = new HashMap<>();

    /**
     * Добавляет вершину в список смежности.
     *
     * @param vertex имя вершины
     * @return true, если вершина добавлена впервые
     */
    @Override
    public boolean addVertex(String vertex) {
        if (adjList.containsKey(vertex)) {
            return false;
        }
        adjList.put(vertex, new ArrayList<>());
        return true;
    }

    /**
     * Удаляет вершину и фильтрует её из списков соседей других вершин.
     *
     * @param vertex имя удаляемой вершины
     * @return true, если вершина существовала
     */
    @Override
    public boolean removeVertex(String vertex) {
        if (!adjList.containsKey(vertex)) {
            return false;
        }
        adjList.remove(vertex);
        for (List<String> neighbors : adjList.values()) {
            neighbors.remove(vertex);
        }
        return true;
    }

    /**
     * Добавляет ребро в список смежности исходной вершины.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро добавлено успешно
     */
    @Override
    public boolean addEdge(String from, String to) {
        addVertex(from);
        addVertex(to);
        List<String> neighbors = adjList.get(from);
        if (neighbors.contains(to)) {
            return false;
        }
        neighbors.add(to);
        return true;
    }

    /**
     * Удаляет ребро из списка соседей исходной вершины.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро существовало и удалено
     */
    @Override
    public boolean removeEdge(String from, String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return false;
        }
        return adjList.get(from).remove(to);
    }

    /**
     * Возвращает копию списка соседей вершины.
     *
     * @param vertex имя вершины
     * @return список смежных вершин
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        if (!adjList.containsKey(vertex)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(adjList.get(vertex));
    }

    /**
     * Возвращает список всех зарегистрированных вершин.
     *
     * @return список вершин
     */
    @Override
    public List<String> getVertices() {
        return new ArrayList<>(adjList.keySet());
    }

    /**
     * Проверяет наличие ребра в списке смежности.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро присутствует
     */
    @Override
    public boolean hasEdge(String from, String to) {
        return adjList.containsKey(from) && adjList.get(from).contains(to);
    }
}