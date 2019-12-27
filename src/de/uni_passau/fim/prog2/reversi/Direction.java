package de.uni_passau.fim.prog2.reversi;

/**
 * Stellt alle Himmelsrichtungen in einem {@code Reversi} Spielbrett dar.
 * Wird benötigt, um die Legalität eines Zuges zu prüfen und den Score zu
 * berechnen.
 *
 * @version 21.12.19
 * @author -----
 */
enum Direction {

    NORTH(0, -1),
    NORTH_EAST(1, -1),
    EAST(1, 0),
    SOUTH_EAST(1, 1),
    SOUTH(0, 1),
    SOUTH_WEST(-1, 1),
    WEST(-1, 0),
    NORTH_WEST(-1, -1);

    /**
     * Entspricht der x-Wert Veränderung für einen Schritt in die
     * entsprechende Himmelsrichtung.
     */
    private final int x;

    /**
     * Entspricht der y-Wert Veränderung für einen Schritt in die
     * entsprechende Himmelsrichtung.
     */
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt die durch die Himmelrichtung definierte x-Wert Veränderung zurück.
     *
     * @return      Entspricht den Schritt in x-Richtung.
     */
    int getX() {
        return x;
    }

    /**
     * Gibt die durch die Himmelrichtung definierte y-Wert Veränderung zurück.
     *
     * @return      Entspricht den Schritt in y-Richtung.
     */
    int getY() {
        return y;
    }
}
