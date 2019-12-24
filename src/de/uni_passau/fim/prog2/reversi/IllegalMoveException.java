package de.uni_passau.fim.prog2.reversi;

/**
 * {@code IllegalMoveException} ist eine definierte {@code RuntimeException},
 * die geworfen wird, falls versucht wird, einen Zug auszuführen, obwohl das
 * Spiel vorbei ist oder ein {@code Player} nicht an der Reihe ist.
 *
 * @version 21.12.19
 * @author -----
 */
class IllegalMoveException extends RuntimeException {

    /**
     * Vewendet lediglich den Super Konstruktor zur Instanziierung der
     * {@code IllegalMoveException}.
     */
    public IllegalMoveException() {
        super();
    }

    /**
     * Verwendet lediglich den Super Konstruktor mit einer Nachricht, um
     * die {@code IllegalMoveException} zu instanziieren.
     *
     * @param message       Entspricht der Fehlernachricht.
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
