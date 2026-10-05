package ru.nsu.egorov;

import java.util.Scanner;

/**
 * Главный класс для запуска консольного приложения Блэкджек.
 */
public class Main {

    /**
     * Запускает основной цикл приложения и инициализирует ввод-вывод.
     *
     * @param args Аргументы командной строки.
     */
    public void run(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleWork tempUi = new ConsoleWork(scanner);
            int numberOfDecks = tempUi.readNumberOfDecks();

            BlackjackGame game = new BlackjackGame(scanner, numberOfDecks);
            game.start();
        }
    }

    /**
     * Точка входа для запуска приложения.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        new Main().run(args);
    }
}