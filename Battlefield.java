package battleship;


import java.util.Arrays;
import java.util.Scanner;

public class Battlefield {

    char[][] battlefield = new char[10][10];
    char[][] battlefieldWithFog = new char[10][10];
    int count = 17;
    Ships ships = new Ships();

    Battlefield() {
        initializeBattlefield();
    }

    private void initializeBattlefield() {
        for (char[] chars : battlefield) {
            Arrays.fill(chars, '~');
        }
    }

    private char[][] initializeFog() {
        char[][] fogOfWar = new char[10][10];
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[i].length; j++) {
                if (battlefield[i][j] == 'O' || battlefield[i][j] == '~') {
                    fogOfWar[i][j] = '~';
                }
                if (battlefield[i][j] == 'M' || battlefield[i][j] == 'X') {
                    fogOfWar[i][j] = battlefield[i][j];
                }
            }
        }
        return fogOfWar;
    }

    public void showBattlefield(boolean isPlacing) {
        if (!isPlacing) {
            battlefieldWithFog = initializeFog();
            printField(battlefieldWithFog);
        } else {
            printField(battlefield);
        }
    }

    public void printField(char[][] field) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < field.length; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] enterCoordinates(String name, int cellSize) {
        System.out.println("Enter the coordinates of the " + name + " (" + cellSize + " cells):");
        Scanner sc = new Scanner(System.in);

        int[] firstCoordinate;
        int[] secondCoordinate;

        String coordinate1 = sc.next();
        String coordinate2 = sc.next();

        if (isValidCoordinates(coordinate1, coordinate2, true)) {
            firstCoordinate = checkedCoordinate(coordinate1);
            secondCoordinate = checkedCoordinate(coordinate2);

            if (firstCoordinate[0] != secondCoordinate[0] && firstCoordinate[1] != secondCoordinate[1]) {
                System.out.println("Error ! Хуйню ввел");
                return enterCoordinates(name, cellSize);
            }

            return new int[][]{firstCoordinate, secondCoordinate};
        } else {
            System.out.println("Error ! Ты ввел хуйню! Try again:");
            return enterCoordinates(name, cellSize);
        }
    }

    public boolean isValidCoordinates(String coordinate1, String coordinate2, boolean placeOrShot) {
        if (placeOrShot) {
            return coordinate1.matches("[A-J]([1-9]|10)") && coordinate2.matches("[A-J]([1-9]|10)");
        } else {
            return coordinate1.matches("[A-J]([1-9]|10)");
        }
    }

    public int[] checkedCoordinate(String coordinate) {
        int coordinate1 = 0;
        int coordinate2 = 0;
        for (int i = 65, j = 0; i <= 74; i++, j++) {
            char x = (char) i;
            if (x == coordinate.charAt(0)) {
                coordinate1 = j;
                break;
            }
        }
        if (coordinate.length() > 2) {
            coordinate2 = 9;
        } else {
            coordinate2 = Integer.parseInt(String.valueOf(coordinate.charAt(1))) - 1;
        }
        return new int[]{coordinate1, coordinate2};
    }

    public boolean setShips() {
        for (Battleships ship : Battleships.values()) {
            int isValid = 0;
            while (isValid != 1) {
                boolean isValidCoordinates = checkShipValidity(ship);
                while (!isValidCoordinates) {
                    isValidCoordinates = checkShipValidity(ship);
                }
                if (ships.placeShip(battlefield)) {
                    isValid++;
                }
            }
            showBattlefield(true);
        }
        return true;
    }

    public boolean checkShipValidity(Battleships ship) {
        int[][] coordinates = enterCoordinates(ship.getName(), ship.getLength());
        int[] xy1 = new int[]{coordinates[0][0], coordinates[0][1]};
        int[] xy2 = new int[]{coordinates[1][0], coordinates[1][1]};
        if (!ships.checkValidityForShips(xy1, xy2, ship.getLength())) {
            System.out.println("Error сука!");
            return checkShipValidity(ship);
        } else {
            return true;
        }
    }

    public boolean toShot(char[][] b) {
        int[] coordinates = shotCoordinates();
        if (b[coordinates[0]][coordinates[1]] == 'O' || b[coordinates[0]][coordinates[1]] == 'X') {
            if (b[coordinates[0]][coordinates[1]] == 'O') {
                count--;
                if (count == 0) {
                    return true;
                }
            }
            b[coordinates[0]][coordinates[1]] = 'X';
            if (isEmptyCellsNearby(b, coordinates[0], coordinates[1])) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }
        } else {
            b[coordinates[0]][coordinates[1]] = 'M';
            System.out.println("You missed!");
        }
        return false;
    }

    public int[] shotCoordinates() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        if (isValidCoordinates(input, "", false)) {
            return checkedCoordinate(input);
        } else {
            System.out.println("Error wrong input");
            return shotCoordinates();
        }
    }

    public boolean isEmptyCellsNearby(char[][] b, int x, int y) {
        if (!getAxisInFog(b, x, y)) {
            try {
                for (int i = y; b[x][i] != '~'; i++) {
                    try {
                        if (b[x][i] == 'O') {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            try {
                for (int i = y; b[x][i] != '~'; i--) {
                    try {
                        if (b[x][i] == 'O') {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }

            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
            return true;
        } else {
            try {
                for (int i = x; b[i][y] != '~'; i++) {
                    try {
                        if (b[i][y] == 'O') {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
            try {
                for (int i = x; b[i][y] != '~'; i--) {
                    try {
                        if (b[i][y] == 'O') {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
            return true;
        }
    }

    public boolean getAxisInFog(char[][] b, int x, int y) {
        try {
            if (b[x][y - 1] == 'O' || b[x][y - 1] == 'X') {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
        try {
            if (b[x][y + 1] == 'O' || b[x][y + 1] == 'X') {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
        return true;
    }
}
