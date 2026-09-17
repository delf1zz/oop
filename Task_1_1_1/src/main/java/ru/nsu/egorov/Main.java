package ru.nsu.egorov;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Сколько чисел хотите отсортировать? ");
        int size = scanner.nextInt();

        int[] arr = new int[size];
        System.out.println("Введите " + size + " чисел через пробел:");
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.println("Исходный массив: " + Arrays.toString(arr));

        HeapSort.sort(arr);

        System.out.println("Отсортированный: " + Arrays.toString(arr));
    }
}