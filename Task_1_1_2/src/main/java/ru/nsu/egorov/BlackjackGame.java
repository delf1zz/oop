package ru.nsu.egorov;

import java.util.Scanner;

/**
 * Класс, управляющий основной логикой раундов игры в Блэкджек.
 */
public class BlackjackGame {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final ConsoleWork ui;

    private int roundNumber = 1;

    public BlackjackGame(Scanner scanner, int numberOfDecks) {
        this.deck = new Deck(numberOfDecks);
        this.player = new Player();
        this.dealer = new Dealer();
        this.ui = new ConsoleWork(scanner);
    }

    public BlackjackGame(Scanner scanner) {
        this(scanner, 1);
    }

    public BlackjackGame(Scanner scanner, Deck deck) {
        this.deck = deck;
        this.player = new Player();
        this.dealer = new Dealer();
        this.ui = new ConsoleWork(scanner);
    }

    public void start() {
        while (true) {
            playRound();
            if (!ui.askPlayNextRound()) {
                break;
            }
            roundNumber++;
        }
        ui.printFinalScore(player, dealer);
    }

    public void playRound() {
        player.getHand().clear();
        dealer.getHand().clear();

        ui.printRoundStart(roundNumber);

        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        ui.printState(player, dealer, false);

        if (!player.getHand().isBlackjack() && !dealer.getHand().isBlackjack()) {
            playerTurn();
        }

        if (!player.getHand().isBust()) {
            dealerTurn();
        }

        determineWinner();
    }

    private void playerTurn() {
        while (ui.askHitOrStand()) {
            Card drawn = deck.drawCard();
            player.getHand().addCard(drawn);
            ui.printPlayerDrawnCard(drawn);
            ui.printState(player, dealer, false);

            if (player.getHand().isBust()) {
                ui.printMessage("Перебор! Вы проиграли раунд.");
                break;
            }
        }
    }

    private void dealerTurn() {
        ui.printMessage("\nХод дилера:");
        ui.printState(player, dealer, true);

        while (dealer.shouldHit()) {
            Card drawn = deck.drawCard();
            dealer.getHand().addCard(drawn);
            ui.printDealerDrawnCard(drawn, dealer);
        }
    }

    private void determineWinner() {
        ui.printMessage("\n--- Результаты раунда ---");
        ui.printState(player, dealer, true);

        int playerScore = player.getHand().getScore();
        int dealerScore = dealer.getHand().getScore();

        if (player.getHand().isBust()) {
            dealer.incrementScore();
        } else if (dealer.getHand().isBust()) {
            ui.printMessage("У дилера перебор! Вы выиграли раунд!");
            player.incrementScore();
        } else if (playerScore > dealerScore) {
            ui.printMessage("Вы выиграли раунд!");
            player.incrementScore();
        } else if (dealerScore > playerScore) {
            ui.printMessage("Дилер выиграл раунд!");
            dealer.incrementScore();
        } else {
            ui.printMessage("Ничья!");
        }

        ui.printScore(player, dealer);
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }
}