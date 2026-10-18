package ru.nsu.egorov;

/**
 * Тесты для графа на основе списков смежности.
 */
class AdjacencyListGraphTest extends BaseGraphTest {

    @Override
    protected Graph createGraph() {
        return new AdjacencyListGraph();
    }
}