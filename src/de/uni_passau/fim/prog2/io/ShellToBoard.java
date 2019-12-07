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
     * {@code Board} Spielobjekt des Programms
     */
    private static Board board = new Reversi();

    private ShellToBoard() { }

    /**
     * Setzt das {@code Board} Spielobjekt vollständig zurück.
     *
     * @see         Board
     */
    static void newBoard() {

    }

    static void move(String[] tokens) {

    }

    static void setLevel(String parameter) {

    }

    static void switchPlayerOrder() {

    }

    static void print() {
        System.out.println(board);
    }

    /**
     * Gibt alle möglichen Kommandos in der Konsole aus.
     */
    static void help() {
        String[] commands = {"-----",
                "Reversi:",
                "h: prints all commands",
                "q: system quit",
                "n: creates a new Game",
                "m <integer x> <integer y>: sets stone to row x, col y",
                "l <integer x>: Sets the level to x ",
                "s: Switches the Player Order",
                "p: prints the current board",
                "-----"};

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
     * Prüft, ob alle nötigen Parameter zu {@code int} konvertiert werden
     * können.
     *
     * @param tokens        {@code String[] array} mit den Parametern x, y
     *                      als {@code String}
     * @return
     */
    private static Integer[] checkParameters(String[] tokens) {
        Integer[] parameters = new Integer[tokens.length];
        for (int i = 0; i < parameters.length; i++) {
            try {
                parameters[i] = Integer.parseInt(tokens[i]);
            } catch (NumberFormatException e) {
                printError("Parameter " + i + " is no integer or too long!");
                return null;
            }
        }
        return parameters;
    }
}
