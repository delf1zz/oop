package ru.nsu.egorov;

import java.util.Scanner;

/**
 * Главный класс для запуска консольного приложения Блэкджек.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args Аргументы командной строки.
     */
    public void run(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Добро пожаловать в Блэкджек!");
            System.out.print("Введите количество колод для игры (например, 1, 2, 4): ");

            int numberOfDecks = 1; // значение по умолчанию
            try {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    numberOfDecks = Integer.parseInt(input);
                    if (numberOfDecks < 1) {
                        numberOfDecks = 1;
                        System.out.println("Количество колод не может быть меньше 1. Установлено: 1.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Введено не число. Будет использована 1 колода по умолчанию.");
            }

            BlackjackGame game = new BlackjackGame(scanner, numberOfDecks);
            game.start();
        }
    }

    /**
     * Точка входа для запускного файла.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        new Main().run(args);
    }
}