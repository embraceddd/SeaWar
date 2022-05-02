package battleship;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        boolean order = true;

        Battlefield battlefield = new Battlefield();
        Battlefield battlefield2 = new Battlefield();

        System.out.println("Player 1, place your ships on the game field");
        battlefield.showBattlefield(true);
        battlefield.setShips();
        clearConsole();
        System.out.println("Player 2, place your ships on the game field");
        battlefield2.showBattlefield(true);
        battlefield2.setShips();
        clearConsole();
        System.out.println();
        System.out.println("The game starts");

        while (battlefield.count != 0 || battlefield2.count != 0) {
            if (order) {
                battlefield2.showBattlefield(false);
                System.out.println("---------------------");
                battlefield.showBattlefield(true);
                System.out.println("Player 1, it's your turn:");
                if (battlefield.toShot(battlefield2.battlefield)) {
                    System.out.println("You sank the last ship. You won. Congratulations!...");
                }
                clearConsole();
                order = false;
            } else {
                battlefield.showBattlefield(false);
                System.out.println("---------------------");
                battlefield2.showBattlefield(true);
                System.out.println("Player 2, it's your turn:");
                if (battlefield2.toShot(battlefield.battlefield)) {
                    System.out.println("You sank the last ship. You won. Congratulations!...");
                }
                clearConsole();
                order = true;
            }
        }

    }
    public static void clearConsole() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
