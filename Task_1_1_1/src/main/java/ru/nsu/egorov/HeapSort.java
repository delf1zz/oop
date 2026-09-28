package ru.nsu.egorov;

public class HeapSort {

    public static int[] sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }

        int n = arr.length;

        // Строим кучу (перестраиваем массив в бинарное дерево)
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        // Достаем элементы по одному из кучи в конец массива
        for (int i = n - 1; i > 0; i--) {
            // Меням местами самый большой элемент (корень arr[0]) с последним (arr[i])
            swap(arr, 0, i);

            // Просеиваем новый корень вниз
            siftDown(arr, i, 0);
        }

        return arr;
    }

    // Вспомогательный метод для просеивания элемента вниз
    private static void siftDown(int[] arr, int size, int rootIndex) {
        int largest = rootIndex;
        int left = 2 * rootIndex + 1;
        int right = 2 * rootIndex + 2;

        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        // Если наибольший элемент — не корень
        if (largest != rootIndex) {
            swap(arr, rootIndex, largest);
            siftDown(arr, size, largest);
        }
    }

    // Простой метод для обмена двух элементов в массиве по их индексам
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}