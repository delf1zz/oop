package ru.nsu.egorov;

import java.util.Scanner;

public class BlackjackGame {
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Scanner scanner;

    private int playerScore = 0;
    private int dealerScore = 0;
    private int roundNumber = 1;

    public BlackjackGame(Scanner scanner) {
        this(scanner, new Deck());
    }

    public BlackjackGame(Scanner scanner, Deck deck) {
        this.deck = deck;
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            playRound();
            System.out.println("\nХотите сыграть еще раунд? (1 - Да, 0 - Нет):");
            String input = scanner.nextLine().trim();
            if (!"1".equals(input)) {
                break;
            }
            roundNumber++;
        }

        System.out.println("\nИтоговый счет: Вы " + playerScore +
                " : " + dealerScore + " Дилер");
        System.out.println("Спасибо за игру!");
    }

    public void playRound() {
        playerHand.clear();
        dealerHand.clear();

        System.out.println("\nРаунд " + roundNumber);
        System.out.println("Дилер раздает карты...");

        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        printState(false);

        if (!playerHand.isBlackjack() && !dealerHand.isBlackjack()) {
            playerTurn();
        }

        if (!playerHand.isBust()) {
            dealerTurn();
        }

        determineWinner();
    }

    private void playerTurn() {
        while (true) {
            System.out.println("\nВведите '1', чтобы взять карту, или '0', чтобы остановиться:");
            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                Card drawn = deck.drawCard();
                playerHand.addCard(drawn);
                System.out.println("Вы вытянули карту: " + drawn);
                printState(false);

                if (playerHand.isBust()) {
                    System.out.println("Перебор! Вы проиграли раунд.");
                    break;
                }
            } else if ("0".equals(choice)) {
                break;
            } else {
                System.out.println("Некорректный ввод. Введите 1 или 0.");
            }
        }
    }

    private void dealerTurn() {
        System.out.println("\nХод дилера:");
        printState(true);

        while (dealerHand.getScore() < 17) {
            Card drawn = deck.drawCard();
            dealerHand.addCard(drawn);
            System.out.println("Дилер берет карту: " + drawn);
            System.out.println("Карты дилера: " + dealerHand.getCards() +
                    " => Очки: " + dealerHand.getScore());
        }
    }

    private void determineWinner() {
        System.out.println("\n--- Результаты раунда ---");
        printState(true);

        int playerScoreRound = playerHand.getScore();
        int dealerHandScore = dealerHand.getScore();

        if (playerHand.isBust()) {
            dealerScore++;
        } else if (dealerHand.isBust()) {
            System.out.println("У дилера перебор! Вы выиграли раунд!");
            playerScore++;
        } else if (playerScoreRound > dealerHandScore) {
            System.out.println("Вы выиграли раунд!");
            playerScore++;
        } else if (dealerHandScore > playerScoreRound) {
            System.out.println("Дилер выиграл раунд!");
            dealerScore++;
        } else {
            System.out.println("Ничья!");
        }

        System.out.println("Счет: Вы " + playerScore +
                " : " + dealerScore + " Дилер");
    }

    private void printState(boolean showDealerAll) {
        System.out.println("Ваши карты: " + playerHand.getCards() +
                " => Очки: " + playerHand.getScore());
        if (showDealerAll) {
            System.out.println("Карты дилера: " + dealerHand.getCards() +
                    " => Очки: " + dealerHand.getScore());
        } else {
            Card firstCard = dealerHand.getCards().get(0);
            System.out.println("Карты дилера: [" + firstCard + ", <закрытая карта>]");
        }
    }
}