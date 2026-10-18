package ru.nsu.egorov.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.egorov.Graph;
import ru.nsu.egorov.exception.CycleDetectionException;

/**
 * Реализация топологической сортировки графа методом поиска в глубину (DFS).
 */
public class DfsTopologicalSort implements TopologicalSortStrategy {

    @Override
    public List<String> sort(Graph graph) {
        List<String> result = new ArrayList<>();
        Map<String, TraversalState> states = new HashMap<>();

        for (String vertex : graph.getVertices()) {
            states.put(vertex, TraversalState.UNVISITED);
        }

        for (String vertex : graph.getVertices()) {
            if (states.get(vertex) == TraversalState.UNVISITED) {
                dfs(graph, vertex, states, result);
            }
        }

        Collections.reverse(result);
        return result;
    }

    private void dfs(Graph graph, String current,
                     Map<String, TraversalState> states, List<String> result) {
        states.put(current, TraversalState.VISITING);

        for (String neighbor : graph.getNeighbors(current)) {
            TraversalState state = states.get(neighbor);
            if (state == TraversalState.VISITING) {
                throw new CycleDetectionException(
                        "Обнаружен цикл при переходе из " + current + " в " + neighbor);
            }
            if (state == TraversalState.UNVISITED) {
                dfs(graph, neighbor, states, result);
            }
        }

        states.put(current, TraversalState.VISITED);
        result.add(current);
    }
}