package ru.nsu.egorov;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            BlackjackGame game = new BlackjackGame(scanner);
            game.start();
        }
    }
}