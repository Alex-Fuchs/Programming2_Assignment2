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
     * Setzt das {@code Board} Objekt vollständig zurück, wobei der Eröffner
     * und das Level gleich bleiben.
     *
     * @see     Board#getFirstPlayer()
     * @see     Reversi
     */
    static void newBoard() {
        board = new Reversi(board.getFirstPlayer(), (Reversi) board);
    }


    static void move(String[] tokens) {
        Integer[] parameter = checkParameters(tokens);
        if (parameter != null) {
            if (parameter[0] <= Board.SIZE && parameter[1] <= Board.SIZE) {
                board = board.move(parameter[0], parameter[1]);
            } else {
                printError("At least one Parameter is too big!");
            }
        }
    }

    /**
     * Ändert das Level des momentanen Spiels. Das Level muss ein positiver
     * {@code Integer} sein.
     *
     * @param tokens     Entspricht der Liste der Parameter, hier ist jedoch
     *                   nur ein Parameter, das Level, notwendig.
     * @see              #checkParameters(String[])
     * @see              Board#setLevel(int)
     */
    static void setLevel(String[] tokens) {
        Integer[] parameter = checkParameters(tokens);
        if (parameter != null) {
            board.setLevel(parameter[0]);
        }
    }

    /**
     * Erstellt ein neues Spiel und tauscht den Eröffner, wobei das
     * alte Level erhalten bleibt.
     *
     * @see     Board#getFirstPlayer()
     * @see     Reversi
     */
    static void switchPlayerOrder() {
        if (board.getFirstPlayer() == Player.HUMAN) {
            board = new Reversi(Player.MACHINE, (Reversi) board);
        } else {
            board = new Reversi(Player.HUMAN, (Reversi) board);
        }
    }

    /**
     * Gibt die kanonische Darstellung von {@code board} zurück.
     *
     * @see     Board#toString()
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
     * Prüft, ob alle nötigen Parameter zu {@code Integer} konvertiert werden
     * können und positiv sind.
     *
     * @param tokens        Entspricht den übergebenen Parametern.
     * @return              Gibt null zurück, falls einer der Parameter nicht
     *                      den Anforderungen entspricht.
     *                      Gibt andernfalls die konvertierten Parameter
     *                      zurück.
     */
    private static Integer[] checkParameters(String[] tokens) {
        Integer[] parameters = new Integer[tokens.length];
        for (int i = 0; i < parameters.length; i++) {
            try {
                parameters[i] = Integer.parseInt(tokens[i]);
                if (parameters[i] <= 0) {
                    printError("At least one parameter is not positive!");
                    return null;
                }
            } catch (NumberFormatException e) {
                printError("At least one parameter is no integer or too big!");
                return null;
            }
        }
        return parameters;
    }
}
