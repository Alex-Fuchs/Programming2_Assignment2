package de.uni_passau.fim.prog2.reversi;

/**
 * Reversi kann als menschlicher Spieler vs Bot gespielt werden, wobei dieses
 * Enum die Spieler und deren Steine darstellt.
 *
 * @version 21.12.19
 * @author -----
 */
public enum Player {

    HUMAN {
        @Override
        Player inverse() {
            return MACHINE;
        }
    },
    MACHINE {
        @Override
        Player inverse() {
            return HUMAN;
        }
    };

    /**
     * Gibt den Gegenspieler für einen Spieler zurück.
     *
     * @return      Entspricht dem gegnerischen Spieler.
     */
    abstract Player inverse();
}
