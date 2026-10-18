package ru.nsu.egorov;

/**
 * Тесты для графа на основе матрицы смежности.
 */
class AdjacencyMatrixGraphTest extends BaseGraphTest {

    @Override
    protected Graph createGraph() {
        return new AdjacencyMatrixGraph();
    }
}