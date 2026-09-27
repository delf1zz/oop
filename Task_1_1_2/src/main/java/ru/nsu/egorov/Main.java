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
            BlackjackGame game = new BlackjackGame(scanner);
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