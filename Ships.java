package battleship;

public class Ships {

    int[] xy1;
    int[] xy2;
    int length;

    public boolean checkValidityForShips(int[] xy1, int[] xy2, int length) {
        this.xy1 = xy1;
        this.xy2 = xy2;
        reverseOrder();
        this.length = length;
        return isValidLength();
    }

    public boolean axis() {
        return xy1[0] == xy2[0];
    }

    public boolean isValidLength() {
        if (axis()) {
            return (Math.abs(xy1[1] - xy2[1]) + 1 == length);
        } else {
            return (Math.abs(xy1[0] - xy2[0]) + 1 == length);
        }
    }

    public boolean placeShip(char[][] battlefield) {
        if (isShipsAround(battlefield)) {
            if (axis()) {
                for (int i = xy1[1]; i != xy2[1] + 1; i++) {
                    battlefield[xy1[0]][i] = 'O';
                }
            } else {
                for (int i = xy1[0]; i != xy2[0] + 1; i++) {
                    battlefield[i][xy1[1]] = 'O';
                }
            }
            return true;
        } else {
            return false;
        }
    }

    boolean isShipsAround(char[][] battlefield) {
        if (axis()) {
            for (int i = xy1[1]; i != xy2[1] + 1; i++) {
                if (checkAxis(battlefield, i, xy1)) return false;
            }
            for (int i = xy1[0] - 1; i < xy1[0] + 2; i++) {
                try {
                    if (battlefield[i][xy1[1] - 1] == 'O') {
                        System.out.println("Error boats are too close");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
                try {
                    if (battlefield[i][xy2[1] + 1] == 'O') {
                        System.out.println("Error boats are too close");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
            }
        } else {
            for (int i = xy1[0]; i != xy2[0] + 1; i++) {
                try {
                    if (battlefield[i][xy1[1] + 1] == 'O') {
                        System.out.println("Error boats are too close");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
                try {
                    if (battlefield[i][xy1[1] - 1] == 'O') {
                        System.out.println("Error boats are too close");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
            }
            for (int i = xy1[1] - 1; i != xy2[1] + 2; i++) {
                if (checkAxis(battlefield, i, xy2)) return false;
            }
        }
        return true;
    }

    private boolean checkAxis(char[][] battlefield, int i, int[] xy2) {
        try {
            if (battlefield[xy1[0] - 1][i] == 'O') {
                System.out.println("Error boats are too close");
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
        try {
            if (battlefield[xy2[0] + 1][i] == 'O') {
                System.out.println("Error boats are too close");
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
        return false;
    }

    public void reverseOrder() {
        if (axis()) {
            if (xy1[1] > xy2[1]) {
                int x = 0;
                x = xy1[1];
                xy1[1] = xy2[1];
                xy2[1] = x;
            }
        } else {
            if (xy1[0] > xy2[0]) {
                int x = 0;
                x = xy1[0];
                xy1[0] = xy2[0];
                xy2[0] = x;
            }
        }
    }
}
