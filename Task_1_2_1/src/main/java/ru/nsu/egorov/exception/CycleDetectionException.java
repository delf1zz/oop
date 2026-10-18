package ru.nsu.egorov.exception;

/**
 * Исключение, выбрасываемое при обнаружении цикла в операциях,
 * требующих ацикличности графа (например, при топологической сортировке).
 */
public class CycleDetectionException extends IllegalStateException {

    public CycleDetectionException(String message) {
        super(message);
    }
}