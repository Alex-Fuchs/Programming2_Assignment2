package de.uni_passau.fim.prog2.reversi;

/**
 * Stellt alle Himmelsrichtungen in einem Reversi Spielbrett dar.
 * Wird benötigt, um die Legalität eines Zuges zu prüfen.
 *
 * @version 21.12.19
 * @author -----
 */
public enum Direction {

    NORTH {
        @Override
        int getX() {
            return 0;
        }

        @Override
        int getY() {
            return 1;
        }
    },
    NORTH_EAST {
        @Override
        int getX() {
            return 1;
        }

        @Override
        int getY() {
            return 1;
        }
    },
    EAST {
        @Override
        int getX() {
            return 1;
        }

        @Override
        int getY() {
            return 0;
        }
    },
    SOUTH_EAST {
        @Override
        int getX() {
            return 1;
        }

        @Override
        int getY() {
            return -1;
        }
    },
    SOUTH {
        @Override
        int getX() {
            return 0;
        }

        @Override
        int getY() {
            return -1;
        }
    },
    SOUTH_WEST {
        @Override
        int getX() {
            return -1;
        }

        @Override
        int getY() {
            return -1;
        }
    },
    WEST {
        @Override
        int getX() {
            return -1;
        }

        @Override
        int getY() {
            return 0;
        }
    },
    NORTH_WEST {
        @Override
        int getX() {
            return -1;
        }

        @Override
        int getY() {
            return 1;
        }
    };

    /**
     * Gibt die durch die Himmelrichtung definierte x-Wert Veränderung zurück.
     *
     * @return      Entspricht den Schritt in x-Richtung.
     */
    abstract int getX();

    /**
     * Gibt die durch die Himmelrichtung definierte y-Wert Veränderung zurück.
     *
     * @return      Entspricht den Schritt in y-Richtung.
     */
    abstract int getY();
}
