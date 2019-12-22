package de.uni_passau.fim.prog2.io;

import de.uni_passau.fim.prog2.reversi.Board;
import de.uni_passau.fim.prog2.reversi.Player;
import de.uni_passau.fim.prog2.reversi.Reversi;

/**
 * {@code ShellToBoard} ist eine Utilityklasse, die den Output des Programms
 * steuert und ebenso das Interface {@code Board} und die Klasse {@code Shell}
 * verbindet. Parameter werden auf Richtigkeit geprüft. Befehle werden an das
 * {@code Board} Objekt weitergeleitet.
 *
 * @version 21.12.19
 * @author -----
 */
final class ShellToBoard {

    /**
     * Stellt das Objekt des Spiels dar.
     */
    private static Board board = new Reversi();

    private ShellToBoard() { }

    /**
     * Setzt das {@code Board} Objekt vollständig zurück.
     *
     * @see         Board
     */
    static void newBoard() {
        board = new Reversi(board.getFirstPlayer(), );
    }

    static void move(String[] tokens) {
        Integer row = checkParameter(tokens[0], Board.SIZE);
        Integer col = checkParameter(tokens[1], Board.SIZE);
        if (row != null && col != null) {
            board.move(row, col);
        }
    }

    /**
     * Ändert das Level des momentanen Spiels. Das Level muss ein positiver
     * Integer sein, der kleiner gleich {@code Reversi.MAX_LEVEL} ist.
     *
     * @param token     Entspricht dem neuen Level.
     */
    static void setLevel(String token) {
        Integer parameter = checkParameter(token, Reversi.MAX_LEVEL);
        if (parameter != null) {
            board.setLevel(parameter);
        }
    }

    /**
     * Erstellt ein neues Spiel und ändert die Zugreihenfolge.
     */
    static void switchPlayerOrder() {
        if (board.getFirstPlayer() == Player.HUMAN) {
            board = new Reversi(Player.HUMAN, );
        } else {
            board = new Reversi(Player.MACHINE, );
        }
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
     * @param message       Entspricht einer kurzen Beschreibung des Fehlers.
     */
    static void printError(String message) {
        final String errorMessage = "Error!";
        System.out.println(errorMessage + " " + message);
    }

    /**
     * Prüft, ob der Parameter zu {@code Integer} konvertiert werden kann und
     * ob er den jew Anforderungen genügt. Die Parameter von {@code move}
     * müssen kleiner gleich {@code Board.SIZE} sein. Der Parameter von
     * {@code setLevel} muss kleiner gleich {@code Reversi.MAX_LEVEL} sein.
     * In jedem Fall muss der Parameter größer 0 sein.
     *
     * @param token             Entspricht dem übergebenen Parameter.
     * @param maxValue          Die max Größe des {@code Integer} unterscheidet
     *                          sich je nach obigen Methoden.
     * @return                  Gibt null zurück, falls der {@code String}
     *                          keinen positiven {@code Integer} kleiner gleich
     *                          {@code maxValue} entspricht.
     *                          Gibt andernfalls den {@code Integer} zurück.
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
