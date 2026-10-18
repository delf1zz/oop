package ru.nsu.egorov.edge;

import java.util.Objects;

/**
 * Неизменяемое представление ориентированного ребра графа.
 *
 * @param from начальная вершина
 * @param to конечная вершина
 */
public record Edge(String from, String to) {

    public Edge {
        Objects.requireNonNull(from, "Начальная вершина не может быть null");
        Objects.requireNonNull(to, "Конечная вершина не может быть null");
    }
}