package ru.nsu.egorov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

class HeapSortTest {

    @Test
    void testSampleInput() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        Assertions.assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void testEmptyAndSingleElement() {
        Assertions.assertArrayEquals(new int[]{}, HeapSort.sort(new int[]{}));
        Assertions.assertArrayEquals(new int[]{42}, HeapSort.sort(new int[]{42}));
    }

    @Test
    void testDuplicatesAndNegativeNumbers() {
        int[] input = {-3, 5, -3, 0, 2, 2, -10};
        int[] expected = {-10, -3, -3, 0, 2, 2, 5};
        Assertions.assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void testAlreadySortedAndReverseSorted() {
        int[] sorted = {1, 2, 3, 4, 5, 6};
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, HeapSort.sort(sorted));
    }

    @Test
    void testRandomLargeArray() {
        Random rd = new Random();
        int[] input = rd.ints(10000, -1000, 1000).toArray();
        int[] expected = input.clone();
        Arrays.sort(expected);

        Assertions.assertArrayEquals(expected, HeapSort.sort(input));
    }

    @Test
    void testConstructor() {
        HeapSort heapSort = new HeapSort();
        Assertions.assertNotNull(heapSort);
    }

    @Test
    void testNullInput() {
        Assertions.assertNull(HeapSort.sort(null));
    }
}
