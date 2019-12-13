package de.uni_passau.fim.prog2.io;

import de.uni_passau.fim.prog2.reversi.Board;
import de.uni_passau.fim.prog2.reversi.Reversi;

/**
 * {@code ShellToBoard} ist eine Utilityklasse, die den Output des Programms
 * steuert und ebenso das Interface {@code Board} und die Klasse {@code Shell}
 * verbindet. Parameter werden auf Richtigkeit geprüft. Befehle werden an das
 * {@code Board} Objekt weitergeleitet.
 *
 * @version 14.11.19
 * @author -----
 */
final class ShellToBoard {

    /**
     * {@code Board} Objekt des Programms
     */
    private static Board board = new Reversi();

    private ShellToBoard() { }

    /**
     * Setzt das {@code Board} Objekt vollständig zurück.
     *
     * @see         Board
     */
    static void newBoard() {
        board = new Reversi(board.getFirstPlayer(), )
    }

    static void move(String[] tokens) {

    }

    static void setLevel(String token) {
        Integer parameter = checkParameter(token, );
        if (parameter != null) {
            board
        }
    }

    static void switchPlayerOrder() {

    }

    /**
     * Gibt die kanonische Darstellung von {@code board} zurück.
     */
    static void print() {
        System.out.println(board);
    }

    /**
     * Gibt alle möglichen Kommandos mit Beschreibung in der Konsole aus.
     */
    static void help() {
        String[] commands = {"**********",
                "Othello:",
                "h: prints all commands",
                "q: game quit",
                "n: creates a new game",
                "m <integer x> <integer y>: sets stone to row x, col y",
                "l <integer x>: sets the level to x",
                "s: switches the player order",
                "p: prints the current board",
                "The board is always square with the size: " + Board.SIZE,
                "The row and col is indexed with 1,...," + Board.SIZE,
                "the Level can be set to 1,...,8",
                "**********"};

        for (String tmp: commands) {
            System.out.println(tmp);
        }
    }

    /**
     * Gibt eine spezielle Fehlernachricht in der Konsole aus.
     *
     * @param message       {@code String} der Fehlernachricht
     */
    static void printError(String message) {
        final String errorMessage = "Error!";
        System.out.println(errorMessage + " " + message);
    }

    /**
     * Prüft, ob der Parameter zu {@code Integer} konvertiert werden kann und
     * ob er den jew Anforderungen genügt. Die Parameter von {@code move}
     * müssen kleiner gleich Board.SIZE sein. Der Parameter von
     * {@code setLevel} muss kleiner gleich dem stärksten Level sein. In jedem
     * Fall muss der Parameter > 0 sein.
     *
     * @param token             {@code String} des Parameters
     * @param maxValue          {@code int} der maximalen Größe des Parameters
     * @return                  null, falls der {@code String} den
     *                          Anforderungen nicht genügt.
     *                          Integer andernfalls.
     */
    private static Integer checkParameter(String token, int maxValue) {
        Integer parameter;
        try {
            parameter = Integer.parseInt(token);
            if (parameter <= maxValue && parameter > 0) {
                return parameter;
            } else {
                printError("A parameter does not fit the conditions");
            }
        } catch (NumberFormatException e) {
            printError("A parameter is no integer or too big");
        }
        return null;
    }
}
