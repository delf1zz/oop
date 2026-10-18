package ru.nsu.egorov;

import java.io.File;
import java.io.IOException;
import java.util.List;
import ru.nsu.egorov.sort.TopologicalSortStrategy;

/**
 * Интерфейс ориентированного графа с вершинами строкового типа.
 */
public interface Graph {

    /**
     * Добавляет новую вершину в граф.
     *
     * @param vertex имя добавляемой вершины
     * @return true, если вершина успешно добавлена; false, если уже существовала
     */
    boolean addVertex(String vertex);

    /**
     * Удаляет вершину и все инцидентные ей ребра.
     *
     * @param vertex имя удаляемой вершины
     * @return true, если вершина найдена и удалена; false в противном случае
     */
    boolean removeVertex(String vertex);

    /**
     * Добавляет ориентированное ребро от вершины from к вершине to.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро добавлено; false, если оно уже существовало
     */
    boolean addEdge(String from, String to);

    /**
     * Удаляет ориентированное ребро между указанными вершинами.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро существовало и удалено; false в противном случае
     */
    boolean removeEdge(String from, String to);

    /**
     * Возвращает список всех исходящих соседей для указанной вершины.
     *
     * @param vertex имя вершины
     * @return список смежных вершин
     */
    List<String> getNeighbors(String vertex);

    /**
     * Возвращает список всех вершин, присутствующих в графе.
     *
     * @return список вершин графа
     */
    List<String> getVertices();

    /**
     * Проверяет наличие ориентированного ребра между вершинами.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     * @return true, если ребро существует; иначе false
     */
    boolean hasEdge(String from, String to);

    /**
     * Заполняет граф данными из текстового файла.
     *
     * @param file файл топологии графа
     * @throws IOException при ошибках ввода-вывода
     */
    void readFromFile(File file) throws IOException;

    /**
     * Выполняет топологическую сортировку вершин алгоритмом по умолчанию.
     *
     * @return список вершин в порядке топологической сортировки
     * @throws ru.nsu.egorov.exception.CycleDetectionException если в графе есть цикл
     */
    List<String> topologicalSort();

    /**
     * Выполняет топологическую сортировку вершин с использованием заданной стратегии.
     *
     * @param strategy алгоритм топологической сортировки
     * @return список вершин в порядке топологической сортировки
     * @throws NullPointerException если переданная стратегия равна null
     */
    List<String> topologicalSort(TopologicalSortStrategy strategy);
}