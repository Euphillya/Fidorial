package fr.fidorial.inventory;

public enum Hand {

    MAIN_HAND(0),
    OFF_HAND(1);

    private final int id;
    Hand(final int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}