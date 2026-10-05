package ru.nsu.egorov;

import java.util.Scanner;

/**
 * Класс, отвечающий за консольный ввод и вывод информации пользователю.
 */
public class ConsoleWork {
    private final Scanner scanner;

    /**
     * Конструктор с указанием объекта чтения ввода.
     *
     * @param scanner Сканер для чтения пользовательского ввода.
     */
    public ConsoleWork(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Запрашивает у пользователя количество колод для игры с валидацией ввода.
     *
     * @return Выбранное количество колод (не менее 1).
     */
    public int readNumberOfDecks() {
        System.out.println("Добро пожаловать в Блэкджек!");
        System.out.print("Введите количество колод для игры (например, 1, 2, 4): ");
        try {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                int decks = Integer.parseInt(input);
                if (decks >= 1) {
                    return decks;
                }
                System.out.println("Количество колод не может быть меньше 1. Установлено: 1.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введено не число. Будет использована 1 колода по умолчанию.");
        }
        return 1;
    }

    /**
     * Запрашивает у игрока выбор действия: взять карту или остановиться.
     *
     * @return true, если игрок берет карту (1); false, если останавливается (0).
     */
    public boolean askHitOrStand() {
        while (true) {
            System.out.println("\nВведите '1', чтобы взять карту, или '0', чтобы остановиться:");
            String choice = scanner.nextLine().trim();
            if ("1".equals(choice)) {
                return true;
            } else if ("0".equals(choice)) {
                return false;
            }
            System.out.println("Некорректный ввод. Введите 1 или 0.");
        }
    }

    /**
     * Запрашивает у пользователя желание продолжить игру на следующий раунд.
     *
     * @return true, если пользователь хочет сыграть еще раунд.
     */
    public boolean askPlayNextRound() {
        System.out.println("\nХотите сыграть еще раунд? (1 - Да, 0 - Нет):");
        String input = scanner.nextLine().trim();
        return "1".equals(input);
    }

    /**
     * Выводит сообщение о начале нового раунда.
     *
     * @param roundNumber Номер текущего раунда.
     */
    public void printRoundStart(int roundNumber) {
        System.out.println("\nРаунд " + roundNumber);
        System.out.println("Дилер раздает карты...");
    }

    /**
     * Выводит текущее состояние карт и очков игрока и дилера.
     *
     * @param player         Объект игрока.
     * @param dealer         Объект дилера.
     * @param showDealerAll Показывать ли все карты дилера открытыми.
     */
    public void printState(Player player, Dealer dealer, boolean showDealerAll) {
        System.out.println("Ваши карты: " + player.getHand().getCards()
                + " => Очки: " + player.getHand().getScore());
        if (showDealerAll) {
            System.out.println("Карты дилера: " + dealer.getHand().getCards()
                    + " => Очки: " + dealer.getHand().getScore());
        } else {
            Card firstCard = dealer.getHand().getCards().get(0);
            System.out.println("Карты дилера: [" + firstCard + ", <закрытая карта>]");
        }
    }

    /**
     * Выводит карту, вытянутую игроком.
     *
     * @param card Вытянутая карта.
     */
    public void printPlayerDrawnCard(Card card) {
        System.out.println("Вы вытянули карту: " + card);
    }

    /**
     * Выводит карту, вытянутую дилером, и его текущее количество очков.
     *
     * @param card   Вытянутая карта.
     * @param dealer Объект дилера.
     */
    public void printDealerDrawnCard(Card card, Dealer dealer) {
        System.out.println("Дилер берет карту: " + card);
        System.out.println("Карты дилера: " + dealer.getHand().getCards()
                + " => Очки: " + dealer.getHand().getScore());
    }

    /**
     * Выводит текстовое сообщение пользователю.
     *
     * @param message Текст сообщения.
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Выводит текущий счет раундов между игроком и дилером.
     *
     * @param player Объект игрока.
     * @param dealer Объект дилера.
     */
    public void printScore(Player player, Dealer dealer) {
        System.out.println("Счет: Вы " + player.getScore()
                + " : " + dealer.getScore() + " Дилер");
    }

    /**
     * Выводит итоговый счет и прощальное сообщение при завершении игры.
     *
     * @param player Объект игрока.
     * @param dealer Объект дилера.
     */
    public void printFinalScore(Player player, Dealer dealer) {
        System.out.println("\nИтоговый счет: Вы "
                + player.getScore() + " : " + dealer.getScore() + " Дилер");
        System.out.println("Спасибо за игру!");
    }
}