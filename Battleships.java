package battleship;

public enum Battleships {

    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private String name;
    private int i;

    Battleships(String s, int i) {
        this.i = i;
        this.name = s;
    }

    public String getName() {
        return this.name;
    }

    public int getLength() {
        return this.i;
    }
}
